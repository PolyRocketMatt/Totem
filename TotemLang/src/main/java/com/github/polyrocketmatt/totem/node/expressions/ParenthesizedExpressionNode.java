package com.github.polyrocketmatt.totem.node.expressions;

import com.github.polyrocketmatt.totem.exception.InterpreterException;
import com.github.polyrocketmatt.totem.interpreter.TotemInterpreter;
import com.github.polyrocketmatt.totem.node.ExpressionNode;
import com.github.polyrocketmatt.totem.node.Node;

/**
 * Created by PolyRocketMatt on 03/10/2020.
 */

public class ParenthesizedExpressionNode extends ExpressionNode {

    private ExpressionNode expression;

    public ParenthesizedExpressionNode(Node superNode, ExpressionNode expression) {
        super(superNode);

        this.expression = expression;
    }

    @Override
    public void visit(TotemInterpreter interpreter) throws InterpreterException {
        expression.visit(interpreter);
    }

    @Override
    public String string(String indent) {
        StringBuilder builder = new StringBuilder();

        builder.append(indent).append("NODE=PARENTHESIZED_EXPR").append("\n");
        builder.append(expression.string(indent + "    "));

        return builder.toString();
    }
}
