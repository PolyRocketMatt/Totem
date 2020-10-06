package com.github.polyrocketmatt.totem.node.statements;

import com.github.polyrocketmatt.totem.exception.InterpreterException;
import com.github.polyrocketmatt.totem.interpreter.TotemInterpreter;
import com.github.polyrocketmatt.totem.lexical.TokenType;
import com.github.polyrocketmatt.totem.node.ExpressionNode;
import com.github.polyrocketmatt.totem.node.Node;
import com.github.polyrocketmatt.totem.parser.AbstractParser;

import java.util.LinkedList;

/**
 * Created by PolyRocketMatt on 05/10/2020.
 */

public class VariableDeclarationStatementNode extends Node {

    private TokenType type;
    private String name;
    private ExpressionNode expression;

    public VariableDeclarationStatementNode(Node superNode, TokenType type, String name) {
        super(superNode, new LinkedList<>());

        this.type = type;
        this.name = name;
    }

    public ExpressionNode getExpression() {
        return expression;
    }

    public void setExpression(ExpressionNode expression) {
        this.expression = expression;
    }

    @Override
    public AbstractParser.NodeType getNodeType() {
        return AbstractParser.NodeType.VARIABLE_DECLARATION_NODE;
    }

    @Override
    public void visit(TotemInterpreter interpreter) throws InterpreterException {

    }

    @Override
    public String string(String indent) {
        StringBuilder builder = new StringBuilder();

        builder.append(indent).append("NODE=VAR_DEC(").append(name).append(")").append("\n");

        for (Node subNode : getSubNodes())
            builder.append(subNode.string(indent + "    "));

        return builder.toString();
    }
}
