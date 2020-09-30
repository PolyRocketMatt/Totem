package com.github.polyrocketmatt.totem.lang.exception;

import com.github.polyrocketmatt.totem.lang.Enums;

/**
 * Created by PolyRocketMatt on 29/09/2020.
 *
 * A ParameterException can be thrown whenever an illegal
 * parameter is being created.
 */

public class ParameterException extends TotemException {

    public ParameterException(String error) {
        super(null, Enums.ColorProfile.ERROR, error);
    }

}
