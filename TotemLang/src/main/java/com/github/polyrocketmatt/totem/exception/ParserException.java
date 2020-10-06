package com.github.polyrocketmatt.totem.exception;

import com.github.polyrocketmatt.totem.Enums;
import com.github.polyrocketmatt.totem.parser.TotemParser;

/**
 * Created by PolyRocketMatt on 29/09/2020.
 *
 * A ParserException can be thrown during syntactic analysis.
 */

public class ParserException extends TotemException {

    public ParserException(TotemParser parser, String error) {
        super(Enums.Phase.SYNTACTIC_ANALYSIS, Enums.ColorProfile.ERROR, error);

        parser.synchronize();
    }

}
