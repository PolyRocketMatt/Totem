package com.github.polyrocketmatt.totem.node.expressions;

import com.github.polyrocketmatt.totem.exception.InterpreterException;
import com.github.polyrocketmatt.totem.interpreter.TotemInterpreter;
import com.github.polyrocketmatt.totem.lexical.Token;
import com.github.polyrocketmatt.totem.lexical.TokenType;
import com.github.polyrocketmatt.totem.node.ExpressionNode;
import com.github.polyrocketmatt.totem.node.Node;
import com.github.polyrocketmatt.totem.utils.RepresentableValue;
import com.github.polyrocketmatt.totem.utils.TypeUtils;
import com.github.polyrocketmatt.totem.utils.Value;

import java.text.MessageFormat;

/**
 * Created by PolyRocketMatt on 03/10/2020.
 *
 * Expression that represents a unary expression.
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
    public void visit(TotemInterpreter interpreter) throws InterpreterException {
        right.visit(interpreter);

        RepresentableValue value = interpreter.getComputationalResults().pop();

        switch (operator.getType()) {
            case PLUS:
                if (value.getType() != TokenType.INT_LITERAL || value.getType() != TokenType.FLOAT_LITERAL)
                    throw new InterpreterException(MessageFormat.format("Cannot bind unary operator {0} to type {1}!",
                            operator.getValue().getValue(), TypeUtils.getTypeOfLiteral(value.getType())));

                if (value.getType() == TokenType.INT_LITERAL) {
                    Integer posIntValue = (Integer) value.getValue();
                    Value<?> posIntTotemValue = new Value<>(+posIntValue, TokenType.INT_LITERAL);

                    interpreter.getComputationalResults().push(new RepresentableValue(posIntTotemValue));
                } else {
                    Float posFloatValue = (Float) value.getValue();
                    Value<?> posFloatTotemValue = new Value<>(+posFloatValue, TokenType.FLOAT_LITERAL);

                    interpreter.getComputationalResults().push(new RepresentableValue(posFloatTotemValue));
                }

                break;
            case MINUS:
                if (value.getType() != TokenType.INT_LITERAL && value.getType() != TokenType.FLOAT_LITERAL)
                    throw new InterpreterException(MessageFormat.format("Cannot bind unary operator {0} to type {1}!",
                            operator.getValue().getValue(), TypeUtils.getTypeOfLiteral(value.getType())));

                if (value.getType() == TokenType.INT_LITERAL) {
                    Integer negIntValue = (Integer) value.getValue();
                    Value<?> negIntTotemValue = new Value<>(-negIntValue, TokenType.INT_LITERAL);

                    interpreter.getComputationalResults().push(new RepresentableValue(negIntTotemValue));
                } else {
                    Float negFloatValue = (Float) value.getValue();
                    Value<?> negFloatTotemValue = new Value<>(-negFloatValue, TokenType.FLOAT_LITERAL);

                    interpreter.getComputationalResults().push(new RepresentableValue(negFloatTotemValue));
                }

                break;
            case EXCLAMATION:
                if (value.getType() != TokenType.BOOL_LITERAL)
                    throw new InterpreterException(MessageFormat.format("Cannot bind unary operator {0} to type {1}!",
                            operator.getValue().getValue(), TypeUtils.getTypeOfLiteral(value.getType())));

                Boolean boolValue = (Boolean) value.getValue();
                Value<?> negatedBoolValue = new Value<>(!boolValue, TokenType.BOOL_LITERAL);

                interpreter.getComputationalResults().push(new RepresentableValue(negatedBoolValue));

                break;
        }
    }

    @Override
    public String string(String indent) {
        StringBuilder builder = new StringBuilder();

        builder.append(indent).append("NODE=UNARY_OP(").append(operator.getType().toString()).append(")").append("\n");
        builder.append(right.string(indent + "    "));

        return builder.toString();
    }
}
