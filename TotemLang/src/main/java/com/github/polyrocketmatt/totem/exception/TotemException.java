package com.github.polyrocketmatt.totem.exception;

import com.github.polyrocketmatt.totem.Enums;

/**
 * Created by PolyRocketMatt on 28/09/2020.
 *
 * A basic exception abstraction for exceptions
 * thrown by Totem.
 */

public class TotemException extends RuntimeException {

    /** The phase in which the exception was thrown */
    private Enums.Phase phase;

    /** The profile that should be used to highlight the error in */
    private Enums.ColorProfile profile;

    /** The actual error message */
    private String error;

    /**
     * Initialize a new TotemException.
     *
     * @param phase the current phase
     * @param profile the color-profile
     * @param error the error message
     */
    public TotemException(Enums.Phase phase, Enums.ColorProfile profile, String error) {
        this.phase = phase;
        this.profile = profile;
        this.error = error;
    }

    /**
     * Get the phase the error was thrown in.
     *
     * @return the phase
     */
    public Enums.Phase getPhase() {
        return phase;
    }

    /**
     * Get the color-profile to be used for highlighting.
     *
     * @return the color-profile
     */
    public Enums.ColorProfile getProfile() {
        return profile;
    }

    /**
     * Get the error message thrown.
     *
     * @return the error message
     */
    public String getError() {
        return error;
    }
}
