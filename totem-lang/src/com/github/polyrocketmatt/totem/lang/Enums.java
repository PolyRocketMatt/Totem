package com.github.polyrocketmatt.totem.lang;

/**
 * Created by PolyRocketMatt on 28/09/2020.
 *
 * Enums that aren't used within the processing
 * of a source.
 */

public class Enums {

    /**
     * Represents a phase of the transpilation process.
     */
    public enum Phase {
        PRE_ANALYSIS,
        LEXICAL_ANALYSIS,
        SYNTACTIC_ANALYSIS,
        TRANSLATION,
        RUNTIME
    }

    /**
     * Represents a highlight color of the console.
     */
    public enum ColorProfile {
        INFO("\u001B[32m"),
        WARNING("\u001B[33m"),
        ERROR("\u001B[31m");

        private String ASCII_REP;

        ColorProfile(String ASCII_REP) {
            this.ASCII_REP = ASCII_REP;
        }

        public String getColor() {
            return ASCII_REP;
        }

        public String getString(String str) {
            return ASCII_REP + str + "\u001B[0m";
        }
    }

}
