package com.github.polyrocketmatt.totem.lexical;

import java.util.LinkedList;
import java.util.Stack;

/**
 * Created by PolyRocketMatt on 29/09/2020.
 *
 * A representation of a stream of tokens.
 */

public class TokenStream {

    /** The stream of tokens is represented as a linked list, since the order does matter! */
    private LinkedList<Token> stream;

    /**
     * Initialize a new stream of tokens.
     */
    public TokenStream() {
        this.stream = new LinkedList<>();
    }

    /**
     * Initialize a new stream of tokens with the given list of tokens.
     */
    public TokenStream(LinkedList<Token> stream) {
        this.stream = stream;
    }

    /**
     * Get the stream of tokens.
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

    /**
     * Reverses the whole stack of tokens in preparation of reading.
     */
    public void close() {
        Stack<Token> tmp = new Stack<>();

        while (!stream.isEmpty())
            tmp.add(stream.pop());

        this.stream = new LinkedList<>(tmp);
    }

    /**
     * Pop the current token in the stream.
     *
     * @return the current token in the stream
     */
    public Token pop() {
        return stream.pop();
    }

    /**
     * Read the current token in the stream but
     * don't remove it from the stream.
     *
     * @return the current token in the stream.
     */
    public Token read() {
        return stream.getLast();
    }

    /**
     * Skip n tokens in the stream.
     *
     * @param n the number of tokens to skip
     */
    public void skip(int n) {
        while (n > 0 && stream.size() > 0)
            stream.pop();
    }

    /**
     * Read the n-th token from the current queued token.
     *
     * @param offset the offset to read the token from
     * @return the offset token
     */
    public Token peek(int offset) {
        return stream.get(stream.size() - (offset + 1));
    }

    /**
     * Split the stream of tokens into two separate streams.
     * The token at the offset-location is included within the first stream.
     *
     * @param offset the offset where the stream should be split
     * @return an array of streams containing 2 streams
     */
    public TokenStream[] split(int offset) {
        TokenStream local = new TokenStream();

        for (int i = 0; i <= offset; i++) {
            local.add(pop());
        }

        return new TokenStream[] { local, this };
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder().append("\n");

        for (Token token : stream)
            str.append(token.toString()).append("\n");
        str.append("\n");

        return str.toString();
    }
}
