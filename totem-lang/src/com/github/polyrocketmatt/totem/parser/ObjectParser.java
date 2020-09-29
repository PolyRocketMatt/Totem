package com.github.polyrocketmatt.totem.parser;

import com.github.polyrocketmatt.totem.lexical.TokenStream;
import com.github.polyrocketmatt.totem.lexical.TokenType;
import com.github.polyrocketmatt.totem.node.ObjectNode;

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
    public ObjectNode parse(TokenStream stream) {
        return null;
    }

    @Override
    public boolean accepts(TokenStream stream) {
        return stream.peek(0).getType().equals(TokenType.DEF)
                && stream.peek(1).getType().equals(TokenType.IDENTIFIER);
    }
}
