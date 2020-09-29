package com.github.polyrocketmatt.totem.exception;

import com.github.polyrocketmatt.totem.Enums;

import java.text.MessageFormat;

/**
 * Created by PolyRocketMatt on 29/09/2020.
 *
 * A TokenizerException can be thrown during lexical analysis.
 */

public class TokenizerException extends TotemException {

    public TokenizerException(String error, int row, int col) {
        super(Enums.Phase.LEXICAL_ANALYSIS, Enums.ColorProfile.ERROR, MessageFormat.format(error, row, col));
    }

}
