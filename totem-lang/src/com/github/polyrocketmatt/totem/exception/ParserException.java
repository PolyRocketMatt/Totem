package com.github.polyrocketmatt.totem.exception;

import com.github.polyrocketmatt.totem.Enums;

/**
 * Created by PolyRocketMatt on 29/09/2020.
 *
 * A ParserException can be thrown during syntactic analysis.
 */

public class ParserException extends TotemException {

    public ParserException(String error) {
        super(Enums.Phase.SYNTACTIC_ANALYSIS, Enums.ColorProfile.ERROR, error);
    }

}
