package com.github.polyrocketmatt.totem.lexical;

import java.util.regex.Pattern;

/**
 * Created by PolyRocketMatt on 29/09/2020.
 *
 * Data-structure that allows to associate a token-type
 * and a pattern with a single object.
 */

public class TokenData {

    /** The associated token-type the pattern represents in Totem */
    private TokenType type;

    /** The associated pattern */
    private Pattern pattern;

    /**
     * Initialize new TokenData.
     *
     * @param type the type
     * @param pattern the pattern
     */
    public TokenData(TokenType type, Pattern pattern) {
        this.type = type;
        this.pattern = pattern;
    }

    /**
     * Get the token-type of the pattern.
     *
     * @return the type
     */
    public TokenType getType() {
        return type;
    }

    /**
     * Get the pattern of the token-type.
     *
     * @return the pattern
     */
    public Pattern getPattern() {
        return pattern;
    }
}
