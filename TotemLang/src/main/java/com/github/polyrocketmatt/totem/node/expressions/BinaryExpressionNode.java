package com.github.polyrocketmatt.totem.node.expressions;

import com.github.polyrocketmatt.totem.exception.InterpreterException;
import com.github.polyrocketmatt.totem.interpreter.TotemInterpreter;
import com.github.polyrocketmatt.totem.lexical.Token;
import com.github.polyrocketmatt.totem.lexical.TokenType;
import com.github.polyrocketmatt.totem.node.ExpressionNode;
import com.github.polyrocketmatt.totem.node.Node;
import com.github.polyrocketmatt.totem.parser.AbstractParser;
import com.github.polyrocketmatt.totem.utils.RepresentableValue;
import com.github.polyrocketmatt.totem.utils.TypeUtils;
import com.github.polyrocketmatt.totem.utils.Value;

import java.text.MessageFormat;

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
    public AbstractParser.NodeType getNodeType() {
        return AbstractParser.NodeType.EXPRESSION_NODE;
    }

    @Override
    public void visit(TotemInterpreter interpreter) throws InterpreterException {
        left.visit(interpreter);
        right.visit(interpreter);

        RepresentableValue rightValue = interpreter.getRepresentableValues().pop();
        RepresentableValue leftValue = interpreter.getRepresentableValues().pop();

        if (!TypeUtils.isCompatibleForComputation(leftValue.getType(), rightValue.getType()))
            throw new InterpreterException(MessageFormat.format("Cannot bind binary operator {0} to types {1} and {2}!",
                    operator.getValue().getValue(), leftValue.getType().toString(), rightValue.getType().toString()));

        switch (operator.getType()) {
            case PLUS:
                if (leftValue.getType() == TokenType.INT_LITERAL) {
                    Integer intLeft = (Integer) leftValue.getValue();

                    if (rightValue.getType() == TokenType.INT_LITERAL) {
                        Integer intRight = (Integer) rightValue.getValue();

                        interpreter.getRepresentableValues().push(new RepresentableValue(new Value<>(intLeft + intRight, TokenType.INT_LITERAL)));
                    } else {
                        Float floatRight = (Float) rightValue.getValue();

                        interpreter.getRepresentableValues().push(new RepresentableValue(new Value<>(intLeft + floatRight, TokenType.FLOAT_LITERAL)));
                    }
                } else if (leftValue.getType() == TokenType.FLOAT_LITERAL){
                    Float floatLeft = (Float) leftValue.getValue();

                    if (rightValue.getType() == TokenType.INT_LITERAL) {
                        Integer intRight = (Integer) rightValue.getValue();

                        interpreter.getRepresentableValues().push(new RepresentableValue(new Value<>(floatLeft + intRight, TokenType.FLOAT_LITERAL)));
                    } else {
                        Float floatRight = (Float) rightValue.getValue();

                        interpreter.getRepresentableValues().push(new RepresentableValue(new Value<>(floatLeft + floatRight, TokenType.FLOAT_LITERAL)));
                    }
                } else {
                    String stringLeft = (String) leftValue.getValue();
                    String stringRight = (String) rightValue.getValue();

                    interpreter.getRepresentableValues().push(new RepresentableValue(new Value<>(stringLeft + stringRight, TokenType.STRING_LITERAL)));
                }

                break;
            case MINUS:
                if (leftValue.getType() == TokenType.INT_LITERAL) {
                    Integer intLeft = (Integer) leftValue.getValue();

                    if (rightValue.getType() == TokenType.INT_LITERAL) {
                        Integer intRight = (Integer) rightValue.getValue();

                        interpreter.getRepresentableValues().push(new RepresentableValue(new Value<>(intLeft - intRight, TokenType.INT_LITERAL)));
                    } else {
                        Float floatRight = (Float) rightValue.getValue();

                        interpreter.getRepresentableValues().push(new RepresentableValue(new Value<>(intLeft - floatRight, TokenType.FLOAT_LITERAL)));
                    }
                } else {
                    Float floatLeft = (Float) leftValue.getValue();

                    if (rightValue.getType() == TokenType.INT_LITERAL) {
                        Integer intRight = (Integer) rightValue.getValue();

                        interpreter.getRepresentableValues().push(new RepresentableValue(new Value<>(floatLeft - intRight, TokenType.FLOAT_LITERAL)));
                    } else {
                        Float floatRight = (Float) rightValue.getValue();

                        interpreter.getRepresentableValues().push(new RepresentableValue(new Value<>(floatLeft - floatRight, TokenType.FLOAT_LITERAL)));
                    }
                }

                break;
            case ASTERISK:
                if (leftValue.getType() == TokenType.INT_LITERAL) {
                    Integer intLeft = (Integer) leftValue.getValue();

                    if (rightValue.getType() == TokenType.INT_LITERAL) {
                        Integer intRight = (Integer) rightValue.getValue();

                        interpreter.getRepresentableValues().push(new RepresentableValue(new Value<>(intLeft * intRight, TokenType.INT_LITERAL)));
                    } else {
                        Float floatRight = (Float) rightValue.getValue();

                        interpreter.getRepresentableValues().push(new RepresentableValue(new Value<>(intLeft * floatRight, TokenType.FLOAT_LITERAL)));
                    }
                } else {
                    Float floatLeft = (Float) leftValue.getValue();

                    if (rightValue.getType() == TokenType.INT_LITERAL) {
                        Integer intRight = (Integer) rightValue.getValue();

                        interpreter.getRepresentableValues().push(new RepresentableValue(new Value<>(floatLeft * intRight, TokenType.FLOAT_LITERAL)));
                    } else {
                        Float floatRight = (Float) rightValue.getValue();

                        interpreter.getRepresentableValues().push(new RepresentableValue(new Value<>(floatLeft * floatRight, TokenType.FLOAT_LITERAL)));
                    }
                }

                break;
            case F_SLASH:
                if (leftValue.getType() == TokenType.INT_LITERAL) {
                    Integer intLeft = (Integer) leftValue.getValue();

                    if (right instanceof LiteralExpressionNode) {
                        if (rightValue.getType() == TokenType.INT_LITERAL) {
                            if ((Integer) rightValue.getValue() == 0)
                                throw new InterpreterException(MessageFormat.format("Division by 0 at ({0},{1})!",
                                        operator.getRow(), operator.getRow()));
                        } else {
                            if ((Float) rightValue.getValue() == 0.0)
                                throw new InterpreterException(MessageFormat.format("Division by 0 at ({0},{1})!",
                                        operator.getRow(), operator.getRow()));
                        }
                    }

                    if (rightValue.getType() == TokenType.INT_LITERAL) {
                        Integer intRight = (Integer) rightValue.getValue();

                        interpreter.getRepresentableValues().push(new RepresentableValue(new Value<>(intLeft / intRight, TokenType.INT_LITERAL)));
                    } else {
                        Float floatRight = (Float) rightValue.getValue();

                        interpreter.getRepresentableValues().push(new RepresentableValue(new Value<>(intLeft / floatRight, TokenType.FLOAT_LITERAL)));
                    }
                } else {
                    Float floatLeft = (Float) leftValue.getValue();

                    if (rightValue.getType() == TokenType.INT_LITERAL) {
                        Integer intRight = (Integer) rightValue.getValue();

                        interpreter.getRepresentableValues().push(new RepresentableValue(new Value<>(floatLeft / intRight, TokenType.FLOAT_LITERAL)));
                    } else {
                        Float floatRight = (Float) rightValue.getValue();

                        interpreter.getRepresentableValues().push(new RepresentableValue(new Value<>(floatLeft / floatRight, TokenType.FLOAT_LITERAL)));
                    }
                }

                break;
            case MODULO:
                if (leftValue.getType() == TokenType.INT_LITERAL) {
                    Integer intLeft = (Integer) leftValue.getValue();

                    if (rightValue.getType() == TokenType.INT_LITERAL) {
                        Integer intRight = (Integer) rightValue.getValue();

                        interpreter.getRepresentableValues().push(new RepresentableValue(new Value<>(intLeft % intRight, TokenType.INT_LITERAL)));
                    } else {
                        Float floatRight = (Float) rightValue.getValue();

                        interpreter.getRepresentableValues().push(new RepresentableValue(new Value<>(intLeft % floatRight, TokenType.FLOAT_LITERAL)));
                    }
                } else {
                    Float floatLeft = (Float) leftValue.getValue();

                    if (rightValue.getType() == TokenType.INT_LITERAL) {
                        Integer intRight = (Integer) rightValue.getValue();

                        interpreter.getRepresentableValues().push(new RepresentableValue(new Value<>(floatLeft % intRight, TokenType.FLOAT_LITERAL)));
                    } else {
                        Float floatRight = (Float) rightValue.getValue();

                        interpreter.getRepresentableValues().push(new RepresentableValue(new Value<>(floatLeft % floatRight, TokenType.FLOAT_LITERAL)));
                    }
                }

                break;
        }
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
