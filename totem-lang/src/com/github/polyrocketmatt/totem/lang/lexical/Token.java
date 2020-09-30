package com.github.polyrocketmatt.totem.lang.lexical;

import com.github.polyrocketmatt.totem.lang.utils.Value;

/**
 * Created by PolyRocketMatt on 28/09/2020.
 *
 * A token represents an informative piece of source-code
 * essential to decoding the provided source.
 */

public class Token {

    /** The value the token should represents */
    private Value<?> value;

    /** The token-type the token is assigned */
    private TokenType type;

    /** The row the token is found on in the source */
    private int row;

    /** The column in the row the token is found on */
    private int col;

    /**
     * Initialize a new token.
     *
     * @param value the value of the token
     * @param type the type of the token
     * @param row the row of the token
     * @param col the column of the token
     */
    public Token(Value<?> value, TokenType type, int row, int col) {
        this.value = value;
        this.type = type;
        this.row = row;
        this.col = col;
    }

    /**
     * Get the value that the token represents.
     *
     * @return the value
     */
    public Value<?> getValue() {
        return value;
    }

    /**
     * Get the token-type of the token.
     *
     * @return the type
     */
    public TokenType getType() {
        return type;
    }

    /**
     * Get the row the token was found on.
     *
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * Get the column the token was found on.
     *
     * @return the column
     */
    public int getColumn() {
        return col;
    }

    @Override
    public String toString() {
        return "Token{" +
                "value=" + value.getValue().toString() +
                ", type=" + type.toString() +
                ", row=" + row +
                ", col=" + col +
                '}';
    }
}
