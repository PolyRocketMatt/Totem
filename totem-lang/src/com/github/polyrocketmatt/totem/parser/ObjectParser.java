package com.github.polyrocketmatt.totem.parser;

import com.github.polyrocketmatt.totem.exception.ParameterException;
import com.github.polyrocketmatt.totem.exception.ParserException;
import com.github.polyrocketmatt.totem.lexical.Token;
import com.github.polyrocketmatt.totem.lexical.TokenStream;
import com.github.polyrocketmatt.totem.lexical.TokenType;
import com.github.polyrocketmatt.totem.node.ObjectNode;
import com.github.polyrocketmatt.totem.utils.Parameter;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PolyRocketMatt on 29/09/2020.
 *
 * Parser to parse object definitions.
 */

public class ObjectParser extends AbstractParser<ObjectNode> {

    public ObjectParser(TotemParser parser) {
        super(parser);
    }

    @Override
    public ObjectNode parse(TokenStream stream) throws ParserException {
        //  Skip "def"
        stream.skip(1);

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

        return new ObjectNode(name.getValue().getValue().toString(), parameters);
    }

    @Override
    public boolean accepts(TokenStream stream) {
        return stream.peek(0).getType().equals(TokenType.DEF)
                && stream.peek(1).getType().equals(TokenType.IDENTIFIER);
    }
}
