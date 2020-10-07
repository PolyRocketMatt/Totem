package com.github.polyrocketmatt.totem.node;

import com.github.polyrocketmatt.totem.exception.InterpreterException;
import com.github.polyrocketmatt.totem.interpreter.TotemInterpreter;
import com.github.polyrocketmatt.totem.parser.AbstractParser;

import java.util.LinkedList;

/**
 * Created by PolyRocketMatt on 30/09/2020.
 *
 * Represents the parent of any source.
 */

public class ParentNode extends Node {

    /** A representation */
    private String parent = "parent";

    /**
     * Initialize a new ParentNode. NOTE: A ParentNode
     * has no super-node, since it's the highest possible
     * node in the AST already.
     */
    public ParentNode() {
        super(null, new LinkedList<>());
    }

    @Override
    public AbstractParser.NodeType getNodeType() {
        return AbstractParser.NodeType.PARENT_NODE;
    }

    @Override
    public void visit(TotemInterpreter interpreter) throws InterpreterException {
        for (Node subNode : getSubNodes())
            subNode.visit(interpreter);
    }

    @Override
    public String string(String indent) {
        StringBuilder builder = new StringBuilder();

        builder.append("NODE=PARENT").append("\n");

        for (Node subNode : getSubNodes())
            builder.append(subNode.string("    ")).append("\n");

        return builder.toString();
    }

}
