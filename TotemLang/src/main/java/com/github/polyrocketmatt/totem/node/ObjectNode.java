package com.github.polyrocketmatt.totem.node;

import com.github.polyrocketmatt.totem.exception.InterpreterException;
import com.github.polyrocketmatt.totem.interpreter.TotemInterpreter;
import com.github.polyrocketmatt.totem.parser.AbstractParser;
import com.github.polyrocketmatt.totem.utils.Parameter;
import com.github.polyrocketmatt.totem.utils.representables.RepresentableObject;
import com.github.polyrocketmatt.totem.utils.representables.ValueHolder;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by PolyRocketMatt on 03/10/2020.
 *
 * Represents an object.
 */

public class ObjectNode extends Node {

    /** The name of the object */
    private String name;

    /** The global object parameters */
    private List<Parameter> parameters;

    /**
     * Initialize a new ObjectNode.
     *
     * @param superNode the super-node
     * @param nodes a list of sub-nodes
     * @param name the name of the object
     * @param parameters the parameters
     */
    public ObjectNode(Node superNode, LinkedList<Node> nodes, String name, List<Parameter> parameters) {
        super(superNode, nodes);

        this.name = name;
        this.parameters = parameters;
    }

    @Override
    public AbstractParser.NodeType getNodeType() {
        return AbstractParser.NodeType.OBJECT_NODE;
    }

    @Override
    public void visit(TotemInterpreter interpreter) throws InterpreterException {
        RepresentableObject object = new RepresentableObject(name, parameters);
        ValueHolder previous = interpreter.getHolder();

        interpreter.getRepresentableParent().getObjects().add(object);
        interpreter.setHolder(object);

        for (Node subNode : getSubNodes())
            subNode.visit(interpreter);

        interpreter.setHolder(previous);
    }

    @Override
    public String string(String indent) {
        StringBuilder builder = new StringBuilder();

        builder.append(indent).append("NODE=OBJECT").append("\n");

        for (Node subNode : getSubNodes())
            builder.append(subNode.string(indent + "    "));

        return builder.toString();
    }
}
