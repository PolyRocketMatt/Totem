package com.github.polyrocketmatt.totem.lexical;

import java.util.LinkedList;

/**
 * Created by PolyRocketMatt on 29/09/2020.
 *
 * A representation of a stream of tokens.
 */

public class TokenStream {

    /** The stream of tokens is represented as a linked list, since the order does matter! */
    private LinkedList<Token> stream;

    /**
     * Initialize a new TokenStream.
     */
    public TokenStream() {
        this.stream = new LinkedList<>();
    }

    /**
     * Get the TokenStream.
     *
     * @return the list of tokens
     */
    public LinkedList<Token> getStream() {
        return new LinkedList<>(stream);
    }

    /**
     * Add a token to the stream.
     *
     * @param token the token
     */
    public void add(Token token) {
        stream.add(token);
    }

}
