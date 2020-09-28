package com.github.polyrocketmatt.totem.exception;

import com.github.polyrocketmatt.totem.Enums;

/**
 * Created by PolyRocketMatt on 28/09/2020.
 */

public class TotemException extends Exception {

    private Enums.Phase phase;
    private Enums.ColorProfile profile;
    private String error;

    public TotemException(Enums.Phase phase, Enums.ColorProfile profile, String error) {
        this.phase = phase;
        this.profile = profile;
        this.error = error;
    }

    public Enums.Phase getPhase() {
        return phase;
    }

    public Enums.ColorProfile getProfile() {
        return profile;
    }

    public String getError() {
        return error;
    }
}
