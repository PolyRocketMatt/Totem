package com.github.polyrocketmatt.totem.utils;

/**
 * Created by PolyRocketMatt on 01/10/2020.
 *
 * Precedence keeps the precedence levels of certain
 * operations. The higher the precedence, the
 */

public class Precedence {

    /** All operations with a precedence of 14 */
    public static final int POSTFIX_INCREMENT = 14;
    public static final int POSTFIX_DECREMENT = 14;

    /** All operations with a precedence of 13 */
    public static final int PREFIX_INCREMENT = 13;
    public static final int PREFIX_DECREMENT = 13;
    public static final int UNARY_PLUS = 13;
    public static final int UNARY_MINUS = 13;
    public static final int BITWISE_COMPLEMENT = 13;
    public static final int NEGATION = 13;

    /** All operations with a precedence of 12 */
    public static final int MULTIPLICATION = 12;
    public static final int DIVISION = 12;
    public static final int MODULO = 12;

    /** All operations with a precedence of 11 */
    public static final int ADDITION = 11;
    public static final int SUBTRACTION = 11;

    /** All operations with a precedence of 10 */
    public static final int ROUND = 10;

    /** All operations with a precedence of 9 */
    public static final int SHIFT_LEFT = 9;
    public static final int SHIFT_RIGHT = 9;
    public static final int SIGNED_SHIFT_RIGHT = 9;

    /** All operations with a precedence of 8 */
    public static final int GREATHER_THAN = 8;
    public static final int GREATHER_EQUAL_THAN = 8;
    public static final int LESS_THAN = 8;
    public static final int LESS_EQUAL_THAN = 8;
    public static final int TYPEOF = 8;

    /** All operations with a precedence of 7 */
    public static final int EQUALS = 7;
    public static final int NOT_EQUALS = 7;

    /** All operations with a precedence of 6 */
    public static final int BITWISE_AND = 6;

    /** All operations with a precedence of 5 */
    public static final int BITWISE_XOR = 5;

    /** All operations with a precedence of 4 */
    public static final int BITWISE_OR = 4;

    /** All operations with a precedence of 3 */
    public static final int LOGICAL_AND = 3;

    /** All operations with a precedence of 2 */
    public static final int LOGICAL_OR = 2;

    /** All operations with a precedence of 1 */
    public static final int TERNARY = 1;

    /** All operations with a precedence of 0 */
    public static final int ASSIGNMENT = 0;

}
