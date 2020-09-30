package com.github.polyrocketmatt.totem.exception;

import com.github.polyrocketmatt.totem.Enums;

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
