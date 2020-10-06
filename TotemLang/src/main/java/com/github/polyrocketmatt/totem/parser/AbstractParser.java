package com.github.polyrocketmatt.totem.parser;

import com.github.polyrocketmatt.totem.exception.ParserException;
import com.github.polyrocketmatt.totem.lexical.TokenStream;
import com.github.polyrocketmatt.totem.node.Node;

/**
 * Created by PolyRocketMatt on 02/10/2020.
 *
 * An abstraction for a Totem Parser.
 */

public abstract class AbstractParser<T extends Node> {


    public enum NodeType {
        PARENT_NODE("parent"),
        OBJECT_NODE("object"),

        VARIABLE_DECLARATION_NODE("var_dec"),
        TUPLE_DECLARATION_NODE("tuple_dec"),

        EXPRESSION_NODE("expression");

        private String name;

        NodeType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    /** The general parser */
    private TotemParser parser;

    /** The stream of tokens that has to be parsed */
    private TokenStream stream;

    /**
     * Initialize a new parser.
     *
     * @param parser the general parser
     * @param stream the stream of tokens
     */
    public AbstractParser(TotemParser parser, TokenStream stream) {
        this.parser = parser;
        this.stream = stream;
    }

    /**
     * Get the general parser.
     *
     * @return the general parser
     */
    public TotemParser getParser() {
        return parser;
    }

    /**
     * Get the stream of tokens that has to be parsed.
     *
     * @return the stream of tokens
     */
    public TokenStream getStream() {
        return stream;
    }

    /**
     * Get an array of node-types that are accepted as
     * a parent.
     *
     * @return an array of node-types
     */
    public abstract NodeType[] getAcceptedParents();

    /**
     * Get the node-type of the parsed node.
     *
     * @return the node-type of the parsed node
     */
    public abstract NodeType getParsedType();

    /**
     * Get the name of the parser.
     *
     * @return the name
     */
    public abstract String getName();

    /**
     * Parse the given stream for the given super-node.
     *
     * @param superNode the super-node
     * @throws ParserException if an exception occurs
     * @return a node of type given by generic type T
     */
    public abstract T parse(Node superNode) throws ParserException;

}
