package com.github.polyrocketmatt.totem.parser.parsers;

import com.github.polyrocketmatt.totem.exception.ParserException;
import com.github.polyrocketmatt.totem.lexical.Token;
import com.github.polyrocketmatt.totem.lexical.TokenType;
import com.github.polyrocketmatt.totem.node.Node;
import com.github.polyrocketmatt.totem.node.ObjectNode;
import com.github.polyrocketmatt.totem.parser.AbstractParser;
import com.github.polyrocketmatt.totem.parser.Acceptor;
import com.github.polyrocketmatt.totem.parser.TotemParser;
import com.github.polyrocketmatt.totem.utils.Parameter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by PolyRocketMatt on 03/10/2020.
 */

public class ObjectParser extends AbstractParser<ObjectNode> implements Acceptor {

    public ObjectParser(TotemParser parser) {
        super(parser, parser.getStream());
    }

    @Override
    public ObjectNode parse(Node superNode) throws ParserException {
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

        return new ObjectNode(superNode, new LinkedList<>(), identifier.getValue().getValue().toString(), parameters);
    }

    @Override
    public NodeType[] getAcceptedParents() {
        return new NodeType[] { NodeType.PARENT_NODE };
    }

    @Override
    public NodeType getParsedType() {
        return NodeType.OBJECT_NODE;
    }

    @Override
    public String getName() {
        return "object";
    }

    @Override
    public boolean accepts() {
        return getStream().read().getType() == TokenType.DEF;
    }
}
