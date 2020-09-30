package com.github.polyrocketmatt.totem.lang.utils;

import com.github.polyrocketmatt.totem.lang.lexical.TokenType;

import java.util.Arrays;

/**
 * Created by PolyRocketMatt on 29/09/2020.
 *
 * Some utility functions regarding types.
 */

public class TypeUtils {

    /** All data-types in Totem represented as token-types */
    private static final TokenType[] types = new TokenType[] { TokenType.BOOL, TokenType.FLOAT, TokenType.INT, TokenType.STRING };

    /** All return-types in Totem represented as token-types */
    private static final TokenType[] returnTypes = new TokenType[] { TokenType.BOOL, TokenType.FLOAT, TokenType.INT, TokenType.STRING, TokenType.VOID };

    /**
     * Check if a given token-type is a data-type.
     *
     * @param type the token-type
     * @return true if the token-type is a data-type in Totem
     */
    public static boolean isTypeToken(TokenType type) {
        return Arrays.asList(types).contains(type);
    }

    /**
     * Check if a given token-type is a return-type.
     *
     * @param type the token-type
     * @return true if the token-type is a return-type in Totem
     */
    public static boolean isReturnTypeToken(TokenType type) {
        return Arrays.asList(returnTypes).contains(type);
    }

    /**
     * Check if a given token-type is an identifier.
     *
     * @param type the token-type
     * @return true if the token-type is an identifier
     */
    public static boolean isIdentifier(TokenType type) { return type.equals(TokenType.IDENTIFIER); }

}
