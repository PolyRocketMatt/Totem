package com.github.polyrocketmatt.totem.utils;

import com.github.polyrocketmatt.totem.exception.InterpreterException;
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

    private static final TokenType[] comparables = new TokenType[] { TokenType.EQUALS_EQUALS, TokenType.NOT_EQUALS, TokenType.GREATER_THAN, TokenType.LESS_THAN,
            TokenType.GREATER_EQUALS, TokenType.LESS_EQUALS };

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

    /**
     * Check if a given token-type is a comparable.
     *
     * @param type the token-type
     * @return true if the token-type is a comparable in Totem
     */
    public static boolean isComparable(TokenType type) { return Arrays.asList(comparables).contains(type); }

    /**
     * Find the primitive type of a literal.
     *
     * @param type the literal
     * @return the primitive type if found
     */
    public static TokenType getTypeOfLiteral(TokenType type) {
        switch (type) {
            case BOOL_LITERAL:
                return TokenType.BOOL;
            case FLOAT_LITERAL:
                return TokenType.FLOAT;
            case INT_LITERAL:
                return TokenType.INT;
            case STRING_LITERAL:
                return TokenType.STRING;
        }

        return type;
    }

    /**
     * Check if two types are compatible for operations.
     *
     * @param left the left type
     * @param right the right type
     * @return true if left and right are compatible
     */
    public static boolean isCompatibleForComputation(TokenType left, TokenType right) {
        switch (left) {
            case BOOL_LITERAL:
                return right == TokenType.BOOL_LITERAL;
            case FLOAT_LITERAL:
            case INT_LITERAL:
                return right == TokenType.INT_LITERAL || right == TokenType.FLOAT_LITERAL;
            case STRING_LITERAL:
                return right == TokenType.STRING_LITERAL;
        }

        return false;
    }

}
