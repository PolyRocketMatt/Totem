package com.github.polyrocketmatt.totem.utils;

import com.github.polyrocketmatt.totem.exception.ParameterException;
import com.github.polyrocketmatt.totem.lexical.Token;
import com.github.polyrocketmatt.totem.lexical.TokenType;

import java.text.MessageFormat;

/**
 * Created by PolyRocketMatt on 29/09/2020.
 *
 * A parameter can hold a type, identifier
 * and possible value.
 */

public class Parameter {

    /** The type associated with the parameter */
    private TokenType type;

    /** The identifier associated with the parameter */
    private String name;

    /** The value of the parameter (if present) */
    private Value<?> value;

    /**
     * Initialize a new parameter given the type and identifier.
     *
     * @param type the type
     * @param identifier the identifier
     * @throws ParameterException if an illegal parameter would be created with
     * the given data
     */
    public Parameter(Token type, Token identifier) throws ParameterException {
        if (!TypeUtils.isTypeToken(type.getType()))
            throw new ParameterException(MessageFormat.format("Expected type at ({0},{1}), found {0} instead!",
                    type.getRow(),
                    type.getColumn(),
                    type.getValue().getValue().toString()
            ));
        if (!TypeUtils.isIdentifier(identifier.getType()))
            throw new ParameterException(MessageFormat.format(
                    "Expected identifier at ({0},{1}), found {2} instead!",
                    identifier.getRow(),
                    identifier.getColumn(),
                    identifier.getValue().getValue().toString()
            ));
        this.type = type.getType();
        this.name = identifier.getValue().getValue().toString();
    }

    /**
     * Get the type of the parameter.
     *
     * @return the type
     */
    public TokenType getType() {
        return type;
    }

    /**
     * Get the name of the parameter.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the value of the parameter.
     *
     * @return the value
     */
    public Value<?> getValue() {
        return value;
    }

    /**
     * Set the value of the parameter.
     *
     * @param value the value to be set
     */
    public void setValue(Value<?> value) {
        this.value = value;
    }
}
