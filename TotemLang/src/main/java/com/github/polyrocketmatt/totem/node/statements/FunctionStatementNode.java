package com.github.polyrocketmatt.totem.node.statements;

import com.github.polyrocketmatt.totem.exception.InterpreterException;
import com.github.polyrocketmatt.totem.interpreter.TotemInterpreter;
import com.github.polyrocketmatt.totem.lexical.TokenType;
import com.github.polyrocketmatt.totem.node.Node;
import com.github.polyrocketmatt.totem.parser.AbstractParser;
import com.github.polyrocketmatt.totem.utils.Parameter;
import com.github.polyrocketmatt.totem.utils.representables.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by PolyRocketMatt on 27/12/2020.
 *
 * Represents a function.
 */

public class FunctionStatementNode extends Node {

    /** The return types of the function */
    private List<TokenType> returnTypes;

    /** The name of the function */
    private String name;

    /** The function parameters */
    private List<Parameter> parameters;

    /**
     * Initialize a new FunctionStatementNode
     *
     * @param superNode the super-node
     * @param nodes a list of sub-nodes
     * @param returnTypes a list of return-types TODO: Make this a tuple-thing
     * @param name the name of the function
     * @param parameters the parameters
     */
    public FunctionStatementNode(Node superNode, List<TokenType> returnTypes, String name, List<Parameter> parameters) {
        super(superNode, new LinkedList<>());

        this.returnTypes = returnTypes;
        this.name = name;
        this.parameters = parameters;
    }

    @Override
    public AbstractParser.NodeType getNodeType() {
        return AbstractParser.NodeType.FUNCTION_NODE;
    }

    @Override
    public void visit(TotemInterpreter interpreter) throws InterpreterException {
        RepresentableFunction function = new RepresentableFunction(returnTypes, name, parameters);

        if (interpreter.getRepresentable() instanceof RepresentableParent)
            ((RepresentableParent) interpreter.getRepresentable()).getFunctions().add(function);
        else if (interpreter.getRepresentable() instanceof RepresentableObject)
            ((RepresentableObject) interpreter.getRepresentable()).getFunctions().add(function);
        RepresentableEntry entry = interpreter.save();

        interpreter.restore(new RepresentableEntry(function, function));

        for (Node subNode : getSubNodes())
            subNode.visit(interpreter);

        interpreter.restore(entry);
    }

    @Override
    public String string(String indent) {
        StringBuilder builder = new StringBuilder();

        builder.append(indent).append("NODE=FUNCTION(").append(name).append(")").append("\n");

        for (Node subNode : getSubNodes())
            builder.append(subNode.string(indent + "    "));

        return builder.toString();
    }
}
