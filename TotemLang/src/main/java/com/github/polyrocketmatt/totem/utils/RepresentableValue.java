package com.github.polyrocketmatt.totem.utils;

/**
 * Created by PolyRocketMatt on 02/10/2020.
 */

public class RepresentableValue {

    private Value<?> totemValue;
    private Object value;

    public RepresentableValue(Value<?> totemValue) {
        this.totemValue = totemValue;
        this.value = totemValue.getValue();
    }

    public Value<?> getTotemValue() {
        return totemValue;
    }

    public Object getValue() {
        return value;
    }
}
