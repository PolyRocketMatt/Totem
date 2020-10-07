package com.github.polyrocketmatt.totem.utils.representables;

import com.github.polyrocketmatt.totem.lexical.TokenType;

/**
 * Created by PolyRocketMatt on 06/10/2020.
 *
 * Represents a variable.
 */

public class RepresentableVariable extends Representable {

    /** The token-type of the variable */
    private TokenType type;

    /** The name of the variable */
    private String name;

    /** The value of the variable */
    private RepresentableValue value;

    /**
     * Initialize a new RepresentableVariable.
     *
     * @param type the token-type
     * @param name the name
     * @param value the value
     */
    public RepresentableVariable(TokenType type, String name, RepresentableValue value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    /**
     * Get the token-type of the variable.
     *
     * @return the token-type
     */
    public TokenType getType() {
        return type;
    }

    /**
     * Get the name of the variable.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the value of the variable.
     *
     * @return the value
     */
    public RepresentableValue getValue() {
        return value;
    }

    @Override
    public String represent(String indent) {
        StringBuilder builder = new StringBuilder();
        builder.append(indent).append(type.toString()).append(": ").append(name);

        if (value.getTotemValue() != null)
            builder.append(": ").append(value.represent("")).append("\n");
        else
            builder.append(": null\n");

        return builder.toString();
    }
}
