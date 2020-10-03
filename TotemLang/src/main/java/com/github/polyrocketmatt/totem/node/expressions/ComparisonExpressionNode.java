package com.github.polyrocketmatt.totem.node.expressions;

import com.github.polyrocketmatt.totem.exception.InterpreterException;
import com.github.polyrocketmatt.totem.interpreter.TotemInterpreter;
import com.github.polyrocketmatt.totem.lexical.Token;
import com.github.polyrocketmatt.totem.lexical.TokenType;
import com.github.polyrocketmatt.totem.node.ExpressionNode;
import com.github.polyrocketmatt.totem.node.Node;
import com.github.polyrocketmatt.totem.utils.RepresentableValue;
import com.github.polyrocketmatt.totem.utils.Value;

import java.text.MessageFormat;

/**
 * Created by PolyRocketMatt on 03/10/2020.
 */

public class ComparisonExpressionNode extends ExpressionNode {

    private ExpressionNode left;
    private ExpressionNode right;
    private Token operator;

    public ComparisonExpressionNode(Node superNode, ExpressionNode left, ExpressionNode right, Token operator) {
        super(superNode);

        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public void visit(TotemInterpreter interpreter) throws InterpreterException {
        left.visit(interpreter);
        right.visit(interpreter);

        RepresentableValue rightValue = interpreter.getComputationalResults().pop();
        RepresentableValue leftValue = interpreter.getComputationalResults().pop();

        switch (operator.getType()) {
            case EQUALS_EQUALS:
                interpreter.getComputationalResults().push(new RepresentableValue(new Value<>(leftValue.getValue().equals(rightValue.getValue()), TokenType.BOOL_LITERAL)));

                break;
            case NOT_EQUALS:
                interpreter.getComputationalResults().push(new RepresentableValue(new Value<>(!leftValue.getValue().equals(rightValue.getValue()), TokenType.BOOL_LITERAL)));

                break;
            case GREATER_THAN:
                if ((leftValue.getType() != TokenType.INT_LITERAL && leftValue.getType() != TokenType.FLOAT_LITERAL) ||
                        (rightValue.getType() != TokenType.INT_LITERAL && rightValue.getType() != TokenType.FLOAT_LITERAL))
                    throw new InterpreterException(MessageFormat.format("Cannot bind comparable operator {0} to types {1} and {2}!",
                            operator.getValue().getValue(), leftValue.getType().toString(), rightValue.getType().toString()));
;
                if (leftValue.getType() == TokenType.INT_LITERAL) {
                    Integer intLeft = (Integer) leftValue.getValue();

                    if (rightValue.getType() == TokenType.INT_LITERAL) {
                        Integer intRight = (Integer) rightValue.getValue();

                        interpreter.getComputationalResults().push(new RepresentableValue(
                                new Value<>(intLeft > intRight, TokenType.BOOL_LITERAL)));
                    } else {
                        Float floatRight = (Float) rightValue.getValue();

                        interpreter.getComputationalResults().push(new RepresentableValue(
                                new Value<>(intLeft > floatRight, TokenType.BOOL_LITERAL)));
                    }
                } else {
                    Float floatLeft = (Float) leftValue.getValue();

                    if (rightValue.getType() == TokenType.INT_LITERAL) {
                        Integer intRight = (Integer) rightValue.getValue();

                        interpreter.getComputationalResults().push(new RepresentableValue(
                                new Value<>(floatLeft > intRight, TokenType.BOOL_LITERAL)));
                    } else {
                        Float floatRight = (Float) rightValue.getValue();

                        interpreter.getComputationalResults().push(new RepresentableValue(
                                new Value<>(floatLeft > floatRight, TokenType.BOOL_LITERAL)));
                    }
                }

                break;
            case GREATER_EQUALS:
                if ((leftValue.getType() != TokenType.INT_LITERAL && leftValue.getType() != TokenType.FLOAT_LITERAL) ||
                        (rightValue.getType() != TokenType.INT_LITERAL && rightValue.getType() != TokenType.FLOAT_LITERAL))
                    throw new InterpreterException(MessageFormat.format("Cannot bind comparable operator {0} to types {1} and {2}!",
                            operator.getValue().getValue(), leftValue.getType().toString(), rightValue.getType().toString()));

                if (leftValue.getType() == TokenType.INT_LITERAL) {
                    Integer intLeft = (Integer) leftValue.getValue();

                    if (rightValue.getType() == TokenType.INT_LITERAL) {
                        Integer intRight = (Integer) rightValue.getValue();

                        interpreter.getComputationalResults().push(new RepresentableValue(
                                new Value<>(intLeft >= intRight, TokenType.BOOL_LITERAL)));
                    } else {
                        Float floatRight = (Float) rightValue.getValue();

                        interpreter.getComputationalResults().push(new RepresentableValue(
                                new Value<>(intLeft >= floatRight, TokenType.BOOL_LITERAL)));
                    }
                } else {
                    Float floatLeft = (Float) leftValue.getValue();

                    if (rightValue.getType() == TokenType.INT_LITERAL) {
                        Integer intRight = (Integer) rightValue.getValue();

                        interpreter.getComputationalResults().push(new RepresentableValue(
                                new Value<>(floatLeft >= intRight, TokenType.BOOL_LITERAL)));
                    } else {
                        Float floatRight = (Float) rightValue.getValue();

                        interpreter.getComputationalResults().push(new RepresentableValue(
                                new Value<>(floatLeft >= floatRight, TokenType.BOOL_LITERAL)));
                    }
                }

                break;
            case LESS_THAN:
                if ((leftValue.getType() != TokenType.INT_LITERAL && leftValue.getType() != TokenType.FLOAT_LITERAL) ||
                        (rightValue.getType() != TokenType.INT_LITERAL && rightValue.getType() != TokenType.FLOAT_LITERAL))
                    throw new InterpreterException(MessageFormat.format("Cannot bind comparable operator {0} to types {1} and {2}!",
                            operator.getValue().getValue(), leftValue.getType().toString(), rightValue.getType().toString()));

                if (leftValue.getType() == TokenType.INT_LITERAL) {
                    Integer intLeft = (Integer) leftValue.getValue();

                    if (rightValue.getType() == TokenType.INT_LITERAL) {
                        Integer intRight = (Integer) rightValue.getValue();

                        interpreter.getComputationalResults().push(new RepresentableValue(
                                new Value<>(intLeft > intRight, TokenType.BOOL_LITERAL)));
                    } else {
                        Float floatRight = (Float) rightValue.getValue();

                        interpreter.getComputationalResults().push(new RepresentableValue(
                                new Value<>(intLeft > floatRight, TokenType.BOOL_LITERAL)));
                    }
                } else {
                    Float floatLeft = (Float) leftValue.getValue();

                    if (rightValue.getType() == TokenType.INT_LITERAL) {
                        Integer intRight = (Integer) rightValue.getValue();

                        interpreter.getComputationalResults().push(new RepresentableValue(
                                new Value<>(floatLeft > intRight, TokenType.BOOL_LITERAL)));
                    } else {
                        Float floatRight = (Float) rightValue.getValue();

                        interpreter.getComputationalResults().push(new RepresentableValue(
                                new Value<>(floatLeft > floatRight, TokenType.BOOL_LITERAL)));
                    }
                }

                break;
            case LESS_EQUALS:
                if ((leftValue.getType() != TokenType.INT_LITERAL && leftValue.getType() != TokenType.FLOAT_LITERAL) ||
                        (rightValue.getType() != TokenType.INT_LITERAL && rightValue.getType() != TokenType.FLOAT_LITERAL))
                    throw new InterpreterException(MessageFormat.format("Cannot bind comparable operator {0} to types {1} and {2}!",
                            operator.getValue().getValue(), leftValue.getType().toString(), rightValue.getType().toString()));

                if (leftValue.getType() == TokenType.INT_LITERAL) {
                    Integer intLeft = (Integer) leftValue.getValue();

                    if (rightValue.getType() == TokenType.INT_LITERAL) {
                        Integer intRight = (Integer) rightValue.getValue();

                        interpreter.getComputationalResults().push(new RepresentableValue(
                                new Value<>(intLeft <= intRight, TokenType.BOOL_LITERAL)));
                    } else {
                        Float floatRight = (Float) rightValue.getValue();

                        interpreter.getComputationalResults().push(new RepresentableValue(
                                new Value<>(intLeft <= floatRight, TokenType.BOOL_LITERAL)));
                    }
                } else {
                    Float floatLeft = (Float) leftValue.getValue();

                    if (rightValue.getType() == TokenType.INT_LITERAL) {
                        Integer intRight = (Integer) rightValue.getValue();

                        interpreter.getComputationalResults().push(new RepresentableValue(
                                new Value<>(floatLeft <= intRight, TokenType.BOOL_LITERAL)));
                    } else {
                        Float floatRight = (Float) rightValue.getValue();

                        interpreter.getComputationalResults().push(new RepresentableValue(
                                new Value<>(floatLeft <= floatRight, TokenType.BOOL_LITERAL)));
                    }
                }

                break;
            case DOUBLE_AMPERSAND:
                if (leftValue.getType() != TokenType.BOOL_LITERAL || rightValue.getType() != TokenType.BOOL_LITERAL)
                    throw new InterpreterException(MessageFormat.format("Cannot bind comparable operator {0} to types {1} and {2}!",
                            operator.getValue().getValue(), leftValue.getType().toString(), rightValue.getType().toString()));

                Boolean boolAndLeft = (Boolean) leftValue.getValue();
                Boolean boolAndRight = (Boolean) rightValue.getValue();

                interpreter.getComputationalResults().push(new RepresentableValue(new Value<>(boolAndLeft && boolAndRight, TokenType.BOOL_LITERAL)));

                break;
            case DOUBLE_PIPE:
                if (leftValue.getType() != TokenType.BOOL_LITERAL || rightValue.getType() != TokenType.BOOL_LITERAL)
                    throw new InterpreterException(MessageFormat.format("Cannot bind comparable operator {0} to types {1} and {2}!",
                            operator.getValue().getValue(), leftValue.getType().toString(), rightValue.getType().toString()));

                Boolean boolOrLeft = (Boolean) leftValue.getValue();
                Boolean boolOrRight = (Boolean) rightValue.getValue();

                interpreter.getComputationalResults().push(new RepresentableValue(new Value<>(boolOrLeft || boolOrRight, TokenType.BOOL_LITERAL)));

                break;
        }
    }

    @Override
    public String string(String indent) {
        StringBuilder builder = new StringBuilder();

        builder.append(indent).append("NODE=COMPARISON_OP(").append(operator.getType().toString()).append(")").append("\n");
        builder.append(left.string(indent + "    "));
        builder.append(right.string(indent + "    "));

        return builder.toString();
    }
}
