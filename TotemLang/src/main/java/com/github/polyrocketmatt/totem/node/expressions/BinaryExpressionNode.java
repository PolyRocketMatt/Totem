package com.github.polyrocketmatt.totem.node.expressions;

import com.github.polyrocketmatt.totem.exception.InterpreterException;
import com.github.polyrocketmatt.totem.lexical.Token;
import com.github.polyrocketmatt.totem.node.ExpressionNode;
import com.github.polyrocketmatt.totem.node.Node;
import com.github.polyrocketmatt.totem.utils.RepresentableValue;

/**
 * Created by PolyRocketMatt on 03/10/2020.
 */

public class BinaryExpressionNode extends ExpressionNode {

    private ExpressionNode left;
    private ExpressionNode right;
    private Token operator;

    public BinaryExpressionNode(Node superNode, ExpressionNode left, ExpressionNode right, Token operator) {
        super(superNode);

        this.left = left;
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

        builder.append(indent).append("NODE=BINARY_OP(").append(operator.getType().toString()).append(")").append("\n");
        builder.append(left.string(indent + "    "));
        builder.append(right.string(indent + "    "));

        return builder.toString();
    }
}
