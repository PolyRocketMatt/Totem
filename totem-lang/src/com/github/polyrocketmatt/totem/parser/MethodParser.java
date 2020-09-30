package com.github.polyrocketmatt.totem.parser;

import com.github.polyrocketmatt.totem.exception.ParameterException;
import com.github.polyrocketmatt.totem.exception.ParserException;
import com.github.polyrocketmatt.totem.lexical.Token;
import com.github.polyrocketmatt.totem.lexical.TokenStream;
import com.github.polyrocketmatt.totem.lexical.TokenType;
import com.github.polyrocketmatt.totem.node.MethodNode;
import com.github.polyrocketmatt.totem.node.ObjectNode;
import com.github.polyrocketmatt.totem.utils.Parameter;
import com.github.polyrocketmatt.totem.utils.TypeUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by PolyRocketMatt on 30/09/2020.
 *
 * Parser to parse method definitions.
 */

public class MethodParser extends AbstractParser<MethodNode> {

    public MethodParser(TotemParser parser) {
        super(parser);
    }

    @Override
    public MethodNode parse(TokenStream stream) throws ParserException {
        if (!(getParser().getSuperNode() instanceof ObjectNode))
            throw new ParserException("Method cannot exist outside of object!");

        //  Skip (
        stream.skip(1);

        List<TokenType> returnTypes = new LinkedList<>();
        Token returnTypeToken = stream.read();

        while (!returnTypeToken.getType().equals(TokenType.CPAREN)) {
            if (TypeUtils.isTypeToken(returnTypeToken.getType()))
                returnTypes.add(returnTypeToken.getType());

            stream.pop();
            returnTypeToken = stream.read();
        }

        stream.pop();

        Token name = stream.read();

        if (!name.getType().equals(TokenType.IDENTIFIER))
            throw new ParserException(MessageFormat.format(
                    "Expected identifier at ({0},{1}), found {2} instead!",
                    name.getRow(),
                    name.getColumn(),
                    name.getValue().getValue().toString()
            ));

        //  Skip identifier and (
        stream.skip(2);

        List<Parameter> parameters = new ArrayList<>();

        while (!stream.read().getType().equals(TokenType.CPAREN)) {
            Token type = stream.pop();
            Token identifier = stream.pop();

            try {
                parameters.add(new Parameter(type, identifier));
            } catch (ParameterException ex) {
                throw new ParserException(ex.getError());
            }

            if (stream.read().getType().equals(TokenType.COMMA))
                stream.pop();
        }

        return new MethodNode((ObjectNode) getParser().getSuperNode(), returnTypes.toArray(new TokenType[returnTypes.size()]),
                name.getValue().getValue().toString(), parameters);
    }

    @Override
    public boolean accepts(TokenStream stream) {
        int offset = 0;
        if (!stream.peek(offset++).getType().equals(TokenType.OPAREN))
            return false;

        while (!stream.peek(offset).getType().equals(TokenType.CPAREN)) {
            if (!TypeUtils.isTypeToken(stream.peek(offset++).getType()))
                return false;

            if (stream.peek(offset).getType().equals(TokenType.COMMA))
                offset++;
        }

        offset++;

        if (!stream.peek(offset++).getType().equals(TokenType.IDENTIFIER))
            return false;

        if (!stream.peek(offset++).getType().equals(TokenType.OPAREN))
            return false;

        while (!stream.peek(offset).getType().equals(TokenType.CPAREN)) {
            if (!TypeUtils.isTypeToken(stream.peek(offset++).getType()))
                return false;
            if (!TypeUtils.isIdentifier(stream.peek(offset++).getType()))
                return false;

            if (stream.peek(offset).getType().equals(TokenType.COMMA))
                offset++;
        }

        return true;
    }
}
