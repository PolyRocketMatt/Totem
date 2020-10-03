package com.github.polyrocketmatt.totem.parser;

import com.github.polyrocketmatt.totem.exception.ParserException;
import com.github.polyrocketmatt.totem.lexical.Token;
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
     * @param current the current token in the stream of tokens
     * @param parser the generic parser
     * @throws ParserException if an exception occurs
     * @return a node of type given by generic type T
     */
    public abstract T parse(Node superNode, Token current, TotemParser parser, Node left) throws ParserException;

}
