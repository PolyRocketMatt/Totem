package com.github.polyrocketmatt.totem.exception;

import com.github.polyrocketmatt.totem.Enums;

/**
 * Created by PolyRocketMatt on 29/09/2020.
 */

public class ParameterException extends TotemException {

    public ParameterException(String error) {
        super(null, Enums.ColorProfile.ERROR, error);
    }

}
