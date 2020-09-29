package com.github.polyrocketmatt.totem.utils;

import com.github.polyrocketmatt.totem.lexical.TokenType;

import java.util.Arrays;

/**
 * Created by PolyRocketMatt on 29/09/2020.
 */

public class TypeUtils {

    private static final TokenType[] types = new TokenType[] { TokenType.BOOL, TokenType.FLOAT, TokenType.INT, TokenType.STRING };

    public static boolean isTypeToken(TokenType type) {
        return Arrays.asList(types).contains(type);
    }

}
