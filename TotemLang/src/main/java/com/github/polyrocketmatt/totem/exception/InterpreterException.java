package com.github.polyrocketmatt.totem.exception;

import com.github.polyrocketmatt.totem.Enums;
import com.github.polyrocketmatt.totem.exception.TotemException;

/**
 * Created by PolyRocketMatt on 30/09/2020.
 *
 * A TranslatorException can be thrown during translation.
 */

public class InterpreterException extends TotemException {

    public InterpreterException(String error) {
        super(Enums.Phase.TRANSLATION, Enums.ColorProfile.ERROR, error);
    }

}
