package com.github.polyrocketmatt.totem.parser.parsers;

import com.github.polyrocketmatt.totem.exception.ParserException;
import com.github.polyrocketmatt.totem.lexical.Token;
import com.github.polyrocketmatt.totem.lexical.TokenType;
import com.github.polyrocketmatt.totem.node.ExpressionNode;
import com.github.polyrocketmatt.totem.node.Node;
import com.github.polyrocketmatt.totem.node.expressions.*;
import com.github.polyrocketmatt.totem.parser.AbstractParser;
import com.github.polyrocketmatt.totem.parser.TotemParser;
import com.github.polyrocketmatt.totem.utils.Value;

import java.text.MessageFormat;

/**
 * Created by PolyRocketMatt on 03/10/2020.
 *
 * Parser to parse expressions.
 */

public class ExpressionParser extends AbstractParser<ExpressionNode> {

    /**
     * Initialize a new ExpressionParser.
     *
     * @param parser the original parser
     */
    public ExpressionParser(TotemParser parser) {
        super(parser, parser.getStream());
    }

    @Override
    public ExpressionNode parse(Node superNode) throws ParserException {
        return parseExpression();
    }

    @Override
    public NodeType[] getAcceptedParents() {
        return new NodeType[] { NodeType.PARENT_NODE, NodeType.OBJECT_NODE, NodeType.EXPRESSION_NODE };
    }

    @Override
    public NodeType getParsedType() {
        return NodeType.EXPRESSION_NODE;
    }

    @Override
    public String getName() {
        return "expr";
    }

    private ExpressionNode parseExpression() throws ParserException {
        return parseEquality();
    }

    private ExpressionNode parseEquality() throws ParserException {
        ExpressionNode expr = parseComparison();
        Token operator = getStream().match(TokenType.EQUALS_EQUALS, TokenType.NOT_EQUALS);

        while (operator != null) {
            ExpressionNode right = parseComparison();

            expr = new ComparisonExpressionNode(getParser().getSuperNode(), expr, right, operator);
            operator = getStream().match(TokenType.EQUALS_EQUALS, TokenType.NOT_EQUALS);
        }

        return expr;
    }

    private ExpressionNode parseComparison() throws ParserException {
        ExpressionNode expr = parseTerm();
        Token operator = getStream().match(TokenType.GREATER_EQUALS, TokenType.LESS_EQUALS, TokenType.GREATER_THAN, TokenType.LESS_THAN, TokenType.DOUBLE_AMPERSAND, TokenType.DOUBLE_PIPE);

        while (operator != null) {
            ExpressionNode right = parseTerm();

            expr = new ComparisonExpressionNode(getParser().getSuperNode(), expr, right, operator);
            operator = getStream().match(TokenType.GREATER_EQUALS, TokenType.LESS_EQUALS, TokenType.GREATER_THAN, TokenType.LESS_THAN);
        }

        return expr;
    }

    //  TODO: In between this needs to come the round operator!
    private ExpressionNode parseTerm() throws ParserException {
        ExpressionNode expr = parseFactor();
        Token operator = getStream().match(TokenType.PLUS, TokenType.MINUS);

        while (operator != null) {
            ExpressionNode right = parseFactor();

            expr = new BinaryExpressionNode(getParser().getSuperNode(), expr, right, operator);
            operator = getStream().match(TokenType.PLUS, TokenType.MINUS);
        }

        return expr;
    }

    private ExpressionNode parseFactor() throws ParserException {
        ExpressionNode expr = parseUnary();
        Token operator = getStream().match(TokenType.ASTERISK, TokenType.F_SLASH, TokenType.MODULO);

        while (operator != null) {
            ExpressionNode right = parseUnary();

            expr = new BinaryExpressionNode(getParser().getSuperNode(), expr, right, operator);
            operator = getStream().match(TokenType.ASTERISK, TokenType.F_SLASH, TokenType.MODULO);
        }

        return expr;
    }

    private ExpressionNode parseUnary() throws ParserException {
        Token operator = getStream().match(TokenType.PLUS, TokenType.MINUS, TokenType.EXCLAMATION);

        if (operator != null) {
            ExpressionNode right = parseUnary();

            return new UnaryExpressionNode(getParser().getSuperNode(), right, operator);
        }

        return parsePrimary();
    }

    private ExpressionNode parsePrimary() throws ParserException {
        Token token = getStream().pop();

        switch (token.getType()) {
            case OPAREN:
                ExpressionNode expr = parseExpression();

                //  TODO: Replace by matchOrThrow
                //  Here we enter "Panic Mode"
                if (getStream().pop().getType() != TokenType.CPAREN)
                    throw new ParserException(getParser(), MessageFormat.format("Expected {0} at ({1},{2}, found {3} instead!",
                            TokenType.CPAREN.toString(), token.getRow(), token.getColumn(), token.getValue().getValue().toString()));

                return new ParenthesizedExpressionNode(getParser().getSuperNode(), expr);
            case NULL:
                return new NullExpressionNode(getParser().getSuperNode());
            case BOOL_LITERAL:
                return new LiteralExpressionNode(getParser().getSuperNode(), new Value<>(Boolean.parseBoolean(token.getValue().getValue().toString()), TokenType.BOOL_LITERAL));
            case FLOAT_LITERAL:
                return new LiteralExpressionNode(getParser().getSuperNode(), new Value<>(Float.parseFloat(token.getValue().getValue().toString()), TokenType.FLOAT_LITERAL));
            case INT_LITERAL:
                return new LiteralExpressionNode(getParser().getSuperNode(), new Value<>(Integer.parseInt(token.getValue().getValue().toString()), TokenType.INT_LITERAL));
            case STRING_LITERAL:
                return new LiteralExpressionNode(getParser().getSuperNode(), new Value<>(token.getValue().getValue().toString(), TokenType.STRING_LITERAL));
        }

        throw new ParserException(getParser(), MessageFormat.format("Expected expression, found {0} instead!", token.getValue().getValue().toString()));
    }

}
