package com.github.polyrocketmatt.totem.exception;

import com.github.polyrocketmatt.totem.lexical.TokenType;

import java.text.MessageFormat;

/**
 * Created by PolyRocketMatt on 02/10/2020.
 */

public class TypeException extends InterpreterException {

    public TypeException(TokenType type) {
        super(MessageFormat.format("Couldn't resolve type {0}!", type.toString()));
    }

}
