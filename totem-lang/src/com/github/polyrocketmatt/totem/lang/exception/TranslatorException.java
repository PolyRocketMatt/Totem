package com.github.polyrocketmatt.totem.lang.exception;

import com.github.polyrocketmatt.totem.lang.Enums;
import com.github.polyrocketmatt.totem.lang.exception.TotemException;

/**
 * Created by PolyRocketMatt on 30/09/2020.
 *
 * A TranslatorException can be thrown during translation.
 */

public class TranslatorException extends TotemException {

    public TranslatorException(String error) {
        super(Enums.Phase.TRANSLATION, Enums.ColorProfile.ERROR, error);
    }

}
