package com.github.polyrocketmatt.totem.utils;

import com.github.polyrocketmatt.totem.lexical.TokenType;

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

    /** All the literals in Totem represented as token-types */
    private static final TokenType[] literals = new TokenType[] { TokenType.BOOL_LITERAL, TokenType.FLOAT_LITERAL, TokenType.INT_LITERAL, TokenType.STRING_LITERAL };

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

    /**
     * Check if a given token-type is a literal.
     *
     * @param type the token-type
     * @return true if the token-type is a literal in Totem
     */
    public static boolean isLiteral(TokenType type) { return Arrays.asList(literals).contains(type); }

}
