package com.github.polyrocketmatt.totem.parser;

import com.github.polyrocketmatt.totem.lexical.Token;
import com.github.polyrocketmatt.totem.lexical.TokenStream;
import com.github.polyrocketmatt.totem.node.LiteralExpression;
import com.github.polyrocketmatt.totem.node.Node;
import com.github.polyrocketmatt.totem.utils.TypeUtils;

/**
 * Created by PolyRocketMatt on 02/10/2020.
 *
 * A parser to parse literals.
 */

public class LiteralParser extends AbstractParser<LiteralExpression> {

    @Override
    public LiteralExpression parse(Node superNode, TokenStream stream) {
        Token literal = stream.pop();

        return new LiteralExpression(superNode, literal.getValue());
    }

    @Override
    public boolean accepts(TokenStream stream) {
        return TypeUtils.isLiteral(stream.peek(0).getType());
    }
}
