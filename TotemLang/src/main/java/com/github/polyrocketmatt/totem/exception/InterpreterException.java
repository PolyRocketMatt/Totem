package com.github.polyrocketmatt.totem.exception;

import com.github.polyrocketmatt.totem.Enums;

/**
 * Created by PolyRocketMatt on 30/09/2020.
 *
 * An InterpreterException can be thrown during interpretation.
 */

public class InterpreterException extends TotemException {

    /**
     * Initialize a new InterpreterException.
     *
     * @param error the error
     */
    public InterpreterException(String error) {
        super(Enums.Phase.TRANSLATION, Enums.ColorProfile.ERROR, error);
    }

}
