package com.github.polyrocketmatt.totem.parser;

import com.github.polyrocketmatt.totem.lexical.TokenStream;
import com.github.polyrocketmatt.totem.node.Node;

/**
 * Created by PolyRocketMatt on 02/10/2020.
 */

public abstract class AbstractParser<T extends Node> {

    public abstract T parse(Node superNode, TokenStream stream);

    public abstract boolean accepts(TokenStream stream);

}
