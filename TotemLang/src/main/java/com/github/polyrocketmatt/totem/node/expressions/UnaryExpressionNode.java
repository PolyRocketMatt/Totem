package com.github.polyrocketmatt.totem.node.expressions;

import com.github.polyrocketmatt.totem.exception.InterpreterException;
import com.github.polyrocketmatt.totem.lexical.Token;
import com.github.polyrocketmatt.totem.node.ExpressionNode;
import com.github.polyrocketmatt.totem.node.Node;
import com.github.polyrocketmatt.totem.utils.RepresentableValue;

/**
 * Created by PolyRocketMatt on 03/10/2020.
 */

public class UnaryExpressionNode extends ExpressionNode {

    private ExpressionNode right;
    private Token operator;

    public UnaryExpressionNode(Node superNode, ExpressionNode right, Token operator) {
        super(superNode);

        this.right = right;
        this.operator = operator;
    }

    @Override
    public RepresentableValue visit() throws InterpreterException {
        return null;
    }

    @Override
    public String string(String indent) {
        StringBuilder builder = new StringBuilder();

        builder.append(indent).append("NODE=UNARY_OP(").append(operator.getType().toString()).append(")").append("\n");
        builder.append(right.string(indent + "    "));

        return builder.toString();
    }
}
