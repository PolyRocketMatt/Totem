package com.github.polyrocketmatt.totem.parser;

import com.github.polyrocketmatt.totem.lexical.TokenStream;
import com.github.polyrocketmatt.totem.node.Node;

/**
 * Created by PolyRocketMatt on 02/10/2020.
 *
 * An abstraction for a Totem Parser.
 */

public abstract class AbstractParser<T extends Node> {

    /**
     * Parse the given stream for the given super-node.
     *
     * @param superNode the super-node
     * @param stream the stream of tokens
     * @return a node of type given by generic type T
     */
    public abstract T parse(Node superNode, TokenStream stream);

    /**
     * Check whether to accept the current stream of tokens.
     *
     * @param stream the stream of tokens
     * @return true if this parser must accept the stream of tokens
     */
    public abstract boolean accepts(TokenStream stream);

}
