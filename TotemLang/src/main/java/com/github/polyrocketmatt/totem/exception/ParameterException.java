package com.github.polyrocketmatt.totem.exception;

import com.github.polyrocketmatt.totem.Enums;

/**
 * Created by PolyRocketMatt on 29/09/2020.
 *
 * A ParameterException can be thrown whenever an illegal
 * parameter is being created.
 */

public class ParameterException extends TotemException {

    /**
     * Initialize a new ParameterException.
     *
     * @param error the error
     */
    public ParameterException(String error) {
        super(null, Enums.ColorProfile.ERROR, error);
    }

}
