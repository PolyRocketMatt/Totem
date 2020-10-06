package com.github.polyrocketmatt.totem.node;

import com.github.polyrocketmatt.totem.exception.InterpreterException;
import com.github.polyrocketmatt.totem.interpreter.TotemInterpreter;
import com.github.polyrocketmatt.totem.parser.AbstractParser;
import com.github.polyrocketmatt.totem.utils.Parameter;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by PolyRocketMatt on 03/10/2020.
 */

public class ObjectNode extends Node {

    private String name;
    private List<Parameter> parameters;

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
