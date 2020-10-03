package com.github.polyrocketmatt.totem.node.expressions;

import com.github.polyrocketmatt.totem.exception.InterpreterException;
import com.github.polyrocketmatt.totem.node.ExpressionNode;
import com.github.polyrocketmatt.totem.node.Node;
import com.github.polyrocketmatt.totem.utils.RepresentableValue;
import com.github.polyrocketmatt.totem.utils.Value;

/**
 * Created by PolyRocketMatt on 03/10/2020.
 */

public class LiteralExpressionNode extends ExpressionNode {

    private Value<?> literal;

    public LiteralExpressionNode(Node superNode, Value<?> literal) {
        super(superNode);

        this.literal = literal;
    }

    @Override
    public RepresentableValue visit() throws InterpreterException {
        return null;
    }

    @Override
    public String string(String indent) {
        StringBuilder builder = new StringBuilder();

        builder.append(indent).append("NODE=LITERAL(").append(literal.getValue().toString()).append(")").append("\n");

        return builder.toString();
    }
}
