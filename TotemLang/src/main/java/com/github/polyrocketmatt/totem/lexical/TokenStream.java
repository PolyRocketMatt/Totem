package com.github.polyrocketmatt.totem.lexical;

import java.util.Arrays;
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
     * Pop the current token in the stream.
     *
     * @return the current token in the stream
     */
    public Token pop() {
        return stream.removeFirst();
    }

    /**
     * Read the current token in the stream but
     * don't remove it from the stream.
     *
     * @return the current token in the stream.
     */
    public Token read() {
        return peek(0);
    }

    /**
     * Skip n tokens in the stream.
     *
     * @param n the number of tokens to skip
     */
    public void skip(int n) {
        while (n > 0 && stream.size() > 0) {
            stream.removeFirst();
            n--;
        }
    }

    /**
     * Read the n-th token from the current queued token.
     *
     * @param offset the offset to read the token from
     * @return the offset token
     */
    public Token peek(int offset) {
        if (stream.size() >= offset + 1)
            return stream.get(offset);
        return null;
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

    /**
     * Check if the current token matches one of the given tokens.
     *
     * @param types the possible types to match
     * @return true if the current token matches one of the types
     */
    public Token match(TokenType... types) {
        for (TokenType type : types) {
            if (!isAtEnd() && read().getType() == type) {
                return stream.pop();   //  Return the token
            }
        }

        return null;
    }

    /**
     * Check if the stream is at the end.
     *
     * @return true if the current token is an EOF token
     */
    public boolean isAtEnd() {
        return read().getType() == TokenType.EOF;
    }

    /**
     * Get the size of the stream of tokens.
     *
     * @return the size
     */
    public int size() {
        return stream.size();
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
