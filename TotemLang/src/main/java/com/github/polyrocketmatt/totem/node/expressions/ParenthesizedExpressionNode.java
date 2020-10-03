package com.github.polyrocketmatt.totem.node.expressions;

import com.github.polyrocketmatt.totem.exception.InterpreterException;
import com.github.polyrocketmatt.totem.node.ExpressionNode;
import com.github.polyrocketmatt.totem.node.Node;
import com.github.polyrocketmatt.totem.utils.RepresentableValue;

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
    public RepresentableValue visit() throws InterpreterException {
        return null;
    }

    @Override
    public String string(String indent) {
        StringBuilder builder = new StringBuilder();

        builder.append(indent).append("NODE=PARENTHESIZED_EXPR").append("\n");
        builder.append(expression.string(indent + "    "));

        return builder.toString();
    }
}
