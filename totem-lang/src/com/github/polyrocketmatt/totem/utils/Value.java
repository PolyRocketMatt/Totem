package com.github.polyrocketmatt.totem.utils;

import com.github.polyrocketmatt.totem.lexical.TokenType;

/**
 * Created by PolyRocketMatt on 28/09/2020.
 *
 * Represents a value of any given Java-type T.
 */

public class Value<T> {

    /** The value that is represented */
    private T value;

    /** The type of token the value should be represented as within Totem */
    private TokenType type;

    /**
     * Create a new value representation.
     *
     * @param value the value to be represented
     * @param type the type it represents within Totem
     */
    public Value(T value, TokenType type) {
        this.value = value;
        this.type = type;
    }

    /**
     * Get the represented value.
     *
     * @return the value
     */
    public T getValue() {
        return value;
    }

    /**
     * Get the type the value represents.
     *
     * @return the type
     */
    public TokenType getType() {
        return type;
    }

}
