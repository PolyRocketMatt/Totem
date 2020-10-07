package com.github.polyrocketmatt.totem.lexical;

import com.github.polyrocketmatt.totem.exception.ParserException;
import com.github.polyrocketmatt.totem.parser.TotemParser;
import com.github.polyrocketmatt.totem.utils.TypeUtils;

import java.text.MessageFormat;
import java.util.LinkedList;

/**
 * Created by PolyRocketMatt on 29/09/2020.
 *
 * A representation of a stream of tokens.
 */

public class TokenStream {

    /** The stream of tokens is represented as a linked list, since the order does matter! */
    private LinkedList<Token> stream;

    /** The parser that uses this stream */
    private TotemParser parser;

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
     * Set the parser for this stream.
     *
     * @param parser the parser
     */
    public void setParser(TotemParser parser) {
        if (this.parser != null)
            throw new ParserException(parser, "Cannot override parser!");

        this.parser = parser;
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
     * Check if the current token matches one of the given tokens.
     *
     * @param types the possible types to match
     * @return true if the current token matches one of the types
     */
    public Token match(TokenType... types) {
        for (TokenType type : types)
            if (!isAtEnd() && read().getType() == type)
                return stream.pop();    //  Return the token

        return null;
    }

    /**
     * Check if the current token matches one of the given tokens.
     *
     * @param types the possible types to match
     * @throws ParserException if none of the given types match the current token-type
     * @return true if the current token matches one of the types
     */
    public Token matchOrThrow(TokenType... types) {
        for (TokenType type : types)
            if (!isAtEnd() && read().getType() == type)
                return stream.pop();    //  Return the token

        throw new ParserException(parser, MessageFormat.format("Expected token of types {1}, found {2} instead!", TypeUtils.getTypesAsString(types), read().getType().toString()));
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
