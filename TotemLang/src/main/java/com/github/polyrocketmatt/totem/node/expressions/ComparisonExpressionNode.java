package com.github.polyrocketmatt.totem.node.expressions;

import com.github.polyrocketmatt.totem.exception.InterpreterException;
import com.github.polyrocketmatt.totem.interpreter.TotemInterpreter;
import com.github.polyrocketmatt.totem.lexical.Token;
import com.github.polyrocketmatt.totem.lexical.TokenType;
import com.github.polyrocketmatt.totem.node.ExpressionNode;
import com.github.polyrocketmatt.totem.node.Node;
import com.github.polyrocketmatt.totem.parser.AbstractParser;
import com.github.polyrocketmatt.totem.utils.representables.RepresentableValue;
import com.github.polyrocketmatt.totem.utils.Value;

import java.text.MessageFormat;

/**
 * Created by PolyRocketMatt on 03/10/2020.
 *
 * Represents an expression created by a comparison.
 */

public class ComparisonExpressionNode extends ExpressionNode {

    /** The left expression */
    private ExpressionNode left;

    /** The right expression */
    private ExpressionNode right;

    /** The comparison operator */
    private Token operator;

    /**
     * Initialize a new ComparisonExpressionNode.
     *
     * @param superNode the super-node
     * @param left the left expression
     * @param right the right expression
     * @param operator the comparison operator
     */
    public ComparisonExpressionNode(Node superNode, ExpressionNode left, ExpressionNode right, Token operator) {
        super(superNode);

        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public AbstractParser.NodeType getNodeType() {
        return AbstractParser.NodeType.EXPRESSION_NODE;
    }

    @Override
    public void visit(TotemInterpreter interpreter) throws InterpreterException {
        left.visit(interpreter);
        right.visit(interpreter);

        RepresentableValue rightValue = interpreter.getRepresentableValues().pop();
        RepresentableValue leftValue = interpreter.getRepresentableValues().pop();

        switch (operator.getType()) {
            case EQUALS_EQUALS:
                interpreter.getRepresentableValues().push(new RepresentableValue(new Value<>(leftValue.getValue().equals(rightValue.getValue()), TokenType.BOOL_LITERAL)));

                break;
            case NOT_EQUALS:
                interpreter.getRepresentableValues().push(new RepresentableValue(new Value<>(!leftValue.getValue().equals(rightValue.getValue()), TokenType.BOOL_LITERAL)));

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

                        interpreter.getRepresentableValues().push(new RepresentableValue(
                                new Value<>(intLeft > intRight, TokenType.BOOL_LITERAL)));
                    } else {
                        Float floatRight = (Float) rightValue.getValue();

                        interpreter.getRepresentableValues().push(new RepresentableValue(
                                new Value<>(intLeft > floatRight, TokenType.BOOL_LITERAL)));
                    }
                } else {
                    Float floatLeft = (Float) leftValue.getValue();

                    if (rightValue.getType() == TokenType.INT_LITERAL) {
                        Integer intRight = (Integer) rightValue.getValue();

                        interpreter.getRepresentableValues().push(new RepresentableValue(
                                new Value<>(floatLeft > intRight, TokenType.BOOL_LITERAL)));
                    } else {
                        Float floatRight = (Float) rightValue.getValue();

                        interpreter.getRepresentableValues().push(new RepresentableValue(
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

                        interpreter.getRepresentableValues().push(new RepresentableValue(
                                new Value<>(intLeft >= intRight, TokenType.BOOL_LITERAL)));
                    } else {
                        Float floatRight = (Float) rightValue.getValue();

                        interpreter.getRepresentableValues().push(new RepresentableValue(
                                new Value<>(intLeft >= floatRight, TokenType.BOOL_LITERAL)));
                    }
                } else {
                    Float floatLeft = (Float) leftValue.getValue();

                    if (rightValue.getType() == TokenType.INT_LITERAL) {
                        Integer intRight = (Integer) rightValue.getValue();

                        interpreter.getRepresentableValues().push(new RepresentableValue(
                                new Value<>(floatLeft >= intRight, TokenType.BOOL_LITERAL)));
                    } else {
                        Float floatRight = (Float) rightValue.getValue();

                        interpreter.getRepresentableValues().push(new RepresentableValue(
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

                        interpreter.getRepresentableValues().push(new RepresentableValue(
                                new Value<>(intLeft > intRight, TokenType.BOOL_LITERAL)));
                    } else {
                        Float floatRight = (Float) rightValue.getValue();

                        interpreter.getRepresentableValues().push(new RepresentableValue(
                                new Value<>(intLeft > floatRight, TokenType.BOOL_LITERAL)));
                    }
                } else {
                    Float floatLeft = (Float) leftValue.getValue();

                    if (rightValue.getType() == TokenType.INT_LITERAL) {
                        Integer intRight = (Integer) rightValue.getValue();

                        interpreter.getRepresentableValues().push(new RepresentableValue(
                                new Value<>(floatLeft > intRight, TokenType.BOOL_LITERAL)));
                    } else {
                        Float floatRight = (Float) rightValue.getValue();

                        interpreter.getRepresentableValues().push(new RepresentableValue(
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

                        interpreter.getRepresentableValues().push(new RepresentableValue(
                                new Value<>(intLeft <= intRight, TokenType.BOOL_LITERAL)));
                    } else {
                        Float floatRight = (Float) rightValue.getValue();

                        interpreter.getRepresentableValues().push(new RepresentableValue(
                                new Value<>(intLeft <= floatRight, TokenType.BOOL_LITERAL)));
                    }
                } else {
                    Float floatLeft = (Float) leftValue.getValue();

                    if (rightValue.getType() == TokenType.INT_LITERAL) {
                        Integer intRight = (Integer) rightValue.getValue();

                        interpreter.getRepresentableValues().push(new RepresentableValue(
                                new Value<>(floatLeft <= intRight, TokenType.BOOL_LITERAL)));
                    } else {
                        Float floatRight = (Float) rightValue.getValue();

                        interpreter.getRepresentableValues().push(new RepresentableValue(
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

                interpreter.getRepresentableValues().push(new RepresentableValue(new Value<>(boolAndLeft && boolAndRight, TokenType.BOOL_LITERAL)));

                break;
            case DOUBLE_PIPE:
                if (leftValue.getType() != TokenType.BOOL_LITERAL || rightValue.getType() != TokenType.BOOL_LITERAL)
                    throw new InterpreterException(MessageFormat.format("Cannot bind comparable operator {0} to types {1} and {2}!",
                            operator.getValue().getValue(), leftValue.getType().toString(), rightValue.getType().toString()));

                Boolean boolOrLeft = (Boolean) leftValue.getValue();
                Boolean boolOrRight = (Boolean) rightValue.getValue();

                interpreter.getRepresentableValues().push(new RepresentableValue(new Value<>(boolOrLeft || boolOrRight, TokenType.BOOL_LITERAL)));

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
