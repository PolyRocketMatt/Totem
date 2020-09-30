package com.github.polyrocketmatt.totem.lang.parser;

import com.github.polyrocketmatt.totem.lang.exception.ParserException;
import com.github.polyrocketmatt.totem.lang.lexical.TokenStream;
import com.github.polyrocketmatt.totem.lang.node.Node;

/**
 * Created by PolyRocketMatt on 29/09/2020.
 *
 * Allow for multi-threaded parsing while still
 * being able to refer to the original parser.
 */

public abstract class AbstractParser<T extends Node> {

    /** The original parser */
    private final TotemParser parser;

    /**
     * Instantiate a new child-parser.
     *
     * @param parser the original parser
     */
    public AbstractParser(TotemParser parser) {
        this.parser = parser;
    }

    /**
     * Get the original parser.
     *
     * @return the original parser
     */
    public TotemParser getParser() {
        return parser;
    }

    /**
     * Parse the given stream of tokens into a new
     * node of type T.
     *
     * @param stream the stream to be parsed
     * @return a node of type T based on the parsed stream
     */
    public abstract T parse(TokenStream stream) throws ParserException;

    /**
     * Check if the parser accepts the coming tokens of the stream.
     *
     * @param stream the stream of tokens
     * @return true if the parser accepts the coming tokens
     */
    public abstract boolean accepts(TokenStream stream);

}
