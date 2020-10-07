package com.github.polyrocketmatt.totem.utils.representables;

import com.github.polyrocketmatt.totem.lexical.TokenType;
import com.github.polyrocketmatt.totem.utils.Value;

/**
 * Created by PolyRocketMatt on 02/10/2020.
 *
 * Represents a value.
 */

public class RepresentableValue extends Representable {

    /** The Totem value of the value */
    private Value<?> totemValue;

    /** The JAVA-Equivalent value */
    private Object value;

    /**
     * Initialize a new RepresentableValue.
     *
     * @param totemValue the Totem value
     */
    public RepresentableValue(Value<?> totemValue) {
        this.totemValue = totemValue;
        this.value = totemValue.getValue();
    }

    /**
     * Get the Totem value.
     *
     * @return the Totem value.
     */
    public Value<?> getTotemValue() {
        return totemValue;
    }

    /**
     * Get the type of the value.
     *
     * @return the type
     */
    public TokenType getType() { return totemValue.getType(); }

    /**
     * Get the JAVA-Equivalent value.
     *
     * @return the JAVA-Equivalent value
     */
    public Object getValue() {
        return value;
    }

    @Override
    public String represent(String indent) {
        return indent + totemValue.getValue().toString();
    }
}
