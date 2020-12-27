package com.github.polyrocketmatt.totem.parser.parsers;

import com.github.polyrocketmatt.totem.exception.ParserException;
import com.github.polyrocketmatt.totem.lexical.Token;
import com.github.polyrocketmatt.totem.lexical.TokenType;
import com.github.polyrocketmatt.totem.node.Node;
import com.github.polyrocketmatt.totem.node.statements.FunctionStatementNode;
import com.github.polyrocketmatt.totem.parser.AbstractParser;
import com.github.polyrocketmatt.totem.parser.Acceptor;
import com.github.polyrocketmatt.totem.parser.TotemParser;
import com.github.polyrocketmatt.totem.utils.Parameter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by PolyRocketMatt on 27/12/2020.
 *
 * Parser to parse function statements.
 */

public class FunctionParser extends AbstractParser<FunctionStatementNode> implements Acceptor {

    /**
     * Initialize a new FunctionParser.
     *
     * @param parser the original parser
     */
    public FunctionParser(TotemParser parser) {
        super(parser, parser.getStream());
    }

    @Override
    public FunctionStatementNode parse(Node superNode) throws ParserException {
        getStream().skip(1);

        Token identifier = getStream().match(TokenType.IDENTIFIER);

        getStream().matchOrThrow(TokenType.OPAREN);

        Token token = getStream().read();
        List<Parameter> parameters = new ArrayList<>();
        while (token.getType() != TokenType.CPAREN) {
            Token type = getStream().matchOrThrow(TokenType.BOOL, TokenType.FLOAT, TokenType.INT, TokenType.STRING);
            Token param = getStream().matchOrThrow(TokenType.IDENTIFIER);

            if (getStream().read().getType() == TokenType.COMMA)
                getStream().skip(1);

            parameters.add(new Parameter(type, param));
            token = getStream().read();
        }

        getStream().skip(1);    //  Skip ")"
        getStream().matchOrThrow(TokenType.ARROW);
        getStream().matchOrThrow(TokenType.OPAREN);

        token = getStream().read();
        List<TokenType> types = new ArrayList<>();
        while (token.getType() != TokenType.CPAREN) {
            Token type = getStream().matchOrThrow(TokenType.BOOL, TokenType.FLOAT, TokenType.INT, TokenType.STRING);

            if (getStream().read().getType() == TokenType.COMMA)
                getStream().skip(1);

            types.add(type.getType());
            token = getStream().read();
        }

        getStream().skip(1);    //  Skip ")"

        return new FunctionStatementNode(superNode, new LinkedList<>(), types, identifier.getValue().getValue().toString(), parameters);
    }

    @Override
    public NodeType[] getAcceptedParents() {
        return new NodeType[] { NodeType.PARENT_NODE, NodeType.OBJECT_NODE };
    }

    @Override
    public NodeType getParsedType() {
        return NodeType.FUNCTION_NODE;
    }

    @Override
    public String getName() {
        return "function";
    }

    @Override
    public boolean accepts() {
        return getStream().read().getType() == TokenType.DEF;
    }

}
