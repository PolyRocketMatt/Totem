package com.github.polyrocketmatt.totem.utils;

import com.github.polyrocketmatt.totem.exception.ParameterException;
import com.github.polyrocketmatt.totem.exception.TotemException;
import com.github.polyrocketmatt.totem.lexical.TokenType;

import java.text.MessageFormat;

/**
 * Created by PolyRocketMatt on 29/09/2020.
 */

public class Parameter {

    private TokenType type;
    private String name;
    private Value<?> value;

    public Parameter(TokenType type, String name) throws TotemException {
        if (!TypeUtils.isTypeToken(type))
            throw new ParameterException(MessageFormat.format("Expected a type, found {0} instead!", type.toString()));
        this.type = type;
        this.name = name;
    }

    public TokenType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Value<?> getValue() {
        return value;
    }

    public void setValue(Value<?> value) {
        this.value = value;
    }
}
