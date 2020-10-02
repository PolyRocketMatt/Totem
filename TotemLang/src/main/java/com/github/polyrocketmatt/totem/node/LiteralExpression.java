package com.github.polyrocketmatt.totem.node;

import com.github.polyrocketmatt.totem.exception.InterpreterException;
import com.github.polyrocketmatt.totem.utils.RepresentableValue;
import com.github.polyrocketmatt.totem.utils.Value;

/**
 * Created by PolyRocketMatt on 02/10/2020.
 */

public class LiteralExpression extends ExpressionNode {

    private Value<?> value;

    public LiteralExpression(Node superNode, Value<?> value) {
        super(superNode);

        this.value = value;
    }

    public Value<?> getValue() {
        return value;
    }

    @Override
    public RepresentableValue visit() throws InterpreterException {
        return new RepresentableValue(value);
    }
}
