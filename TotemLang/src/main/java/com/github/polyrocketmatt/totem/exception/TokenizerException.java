package com.github.polyrocketmatt.totem.exception;

import com.github.polyrocketmatt.totem.Enums;

import java.text.MessageFormat;

/**
 * Created by PolyRocketMatt on 29/09/2020.
 *
 * A TokenizerException can be thrown during lexical analysis.
 */

public class TokenizerException extends TotemException {

    /**
     * Initialize a new TokenizerException.
     *
     * @param error the error
     * @param row the row the error occurred on
     * @param col the column the error occurred on
     * @param line the text that triggered the exception
     */
    public TokenizerException(String error, int row, int col, String line) {
        super(Enums.Phase.LEXICAL_ANALYSIS, Enums.ColorProfile.ERROR, MessageFormat.format(error, row, col, line));
    }

}
