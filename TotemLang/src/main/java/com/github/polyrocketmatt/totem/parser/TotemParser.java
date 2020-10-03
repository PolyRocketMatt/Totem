package com.github.polyrocketmatt.totem.parser;

import com.github.polyrocketmatt.totem.exception.ParserException;
import com.github.polyrocketmatt.totem.lexical.Token;
import com.github.polyrocketmatt.totem.lexical.TokenStream;
import com.github.polyrocketmatt.totem.lexical.TokenType;
import com.github.polyrocketmatt.totem.node.ExpressionNode;
import com.github.polyrocketmatt.totem.node.Node;
import com.github.polyrocketmatt.totem.node.ParentNode;
import com.github.polyrocketmatt.totem.node.expressions.*;
import com.github.polyrocketmatt.totem.utils.Value;

import java.text.MessageFormat;

/**
 * Created by PolyRocketMatt on 01/10/2020.
 */

public class TotemParser {

    private TokenStream stream;
    private ParentNode parentNode;
    private Node superNode;

    public TotemParser(TokenStream stream) {
        this.stream = stream;
        this.parentNode = new ParentNode();
        this.superNode = parentNode;
    }

    public void parse() {
        try {
            ExpressionNode expressionNode = parseExpression();

            superNode.add(expressionNode);

            System.out.println(superNode.string(""));
        } catch (ParserException ex) {
            //  TODO: Add error to error handler

            synchronize();
        }
    }

    private ExpressionNode parseExpression() throws ParserException {
        return parseEquality();
    }

    private ExpressionNode parseEquality() throws ParserException {
        ExpressionNode expr = parseComparison();
        Token operator = stream.match(TokenType.EQUALS_EQUALS, TokenType.NOT_EQUALS);

        while (operator != null) {
            ExpressionNode right = parseComparison();

            expr = new ComparisonExpressionNode(superNode, expr, right, operator);
            operator = stream.match(TokenType.EQUALS_EQUALS, TokenType.NOT_EQUALS);
        }

        return expr;
    }

    private ExpressionNode parseComparison() throws ParserException {
        ExpressionNode expr = parseTerm();
        Token operator = stream.match(TokenType.GREATER_EQUALS, TokenType.LESS_EQUALS, TokenType.GREATER_THAN, TokenType.LESS_THAN);

        while (operator != null) {
            ExpressionNode right = parseTerm();

            expr = new ComparisonExpressionNode(superNode, expr, right, operator);
            operator = stream.match(TokenType.GREATER_EQUALS, TokenType.LESS_EQUALS, TokenType.GREATER_THAN, TokenType.LESS_THAN);
        }

        return expr;
    }

    //  TODO: In between this needs to come the round operator!
    private ExpressionNode parseTerm() throws ParserException {
        ExpressionNode expr = parseFactor();
        Token operator = stream.match(TokenType.PLUS, TokenType.MINUS);

        while (operator != null) {
            ExpressionNode right = parseFactor();

            expr = new BinaryExpressionNode(superNode, expr, right, operator);
            operator = stream.match(TokenType.PLUS, TokenType.MINUS);
        }

        return expr;
    }

    private ExpressionNode parseFactor() throws ParserException {
        ExpressionNode expr = parseUnary();
        Token operator = stream.match(TokenType.ASTERISK, TokenType.F_SLASH, TokenType.MODULO);

        while (operator != null) {
            ExpressionNode right = parseUnary();

            expr = new BinaryExpressionNode(superNode, expr, right, operator);
            operator = stream.match(TokenType.ASTERISK, TokenType.F_SLASH, TokenType.MODULO);
        }

        return expr;
    }

    private ExpressionNode parseUnary() throws ParserException {
        Token operator = stream.match(TokenType.PLUS, TokenType.MINUS, TokenType.EXCLAMATION);

        if (operator != null) {
            ExpressionNode right = parseUnary();

            return new UnaryExpressionNode(superNode, right, operator);
        }

        return parsePrimary();
    }

    private ExpressionNode parsePrimary() throws ParserException {
        Token token = stream.pop();

        switch (token.getType()) {
            case OPAREN:
                ExpressionNode expr = parseExpression();

                //  Here we enter "Panic Mode"
                if (stream.pop().getType() != TokenType.CPAREN)
                    throw new ParserException(MessageFormat.format("Expected {0} at ({1},{2}, found {3} instead!",
                            TokenType.CPAREN.toString(), token.getRow(), token.getColumn(), token.getValue().getValue().toString()));

                return new ParenthesizedExpressionNode(superNode, expr);
            case BOOL_LITERAL:
                return new LiteralExpressionNode(superNode, new Value<>(Boolean.parseBoolean(token.getValue().getValue().toString()), TokenType.BOOL_LITERAL));
            case FLOAT_LITERAL:
                return new LiteralExpressionNode(superNode, new Value<>(Float.parseFloat(token.getValue().getValue().toString()), TokenType.FLOAT_LITERAL));
            case INT_LITERAL:
                return new LiteralExpressionNode(superNode, new Value<>(Integer.parseInt(token.getValue().getValue().toString()), TokenType.INT_LITERAL));
            case STRING_LITERAL:
                return new LiteralExpressionNode(superNode, new Value<>(token.getValue().getValue().toString(), TokenType.STRING_LITERAL));
        }

        throw new ParserException(MessageFormat.format("Expected expression, found {0} instead!", token.getValue().getValue().toString()));
    }

    /**
     * Synchronize the parser to continue parsing when a syntax error
     * has occurred somewhere in a previous expression to avoid
     * cascading/ghost errors.
     */
    private void synchronize() {
        Token token = stream.pop();

        while (!stream.isAtEnd()) {
            if (token.getType() == TokenType.SEMI_COLON)
                return;

            switch (token.getType()) {
                case DEF:
                case FOR:
                case WHILE:
                case REPEAT:
                case IF:
                case ELSE:
                case ELSE_IF:
                case RETURN:
                case PRINT:
                case BOOL:
                case FLOAT:
                case INT:
                case STRING:
                    return;
            }

            token = stream.pop();
        }
    }

}
