package com.github.polyrocketmatt.totem.exception;

import com.github.polyrocketmatt.totem.Utils;

/**
 * Created by PolyRocketMatt on 28/09/2020.
 */

public class TotemException extends Exception {

    private Utils.Phase phase;
    private Utils.ColorProfile profile;
    private String error;

    public TotemException(Utils.Phase phase,  Utils.ColorProfile profile, String error) {
        this.phase = phase;
        this.profile = profile;
        this.error = error;
    }

    public Utils.Phase getPhase() {
        return phase;
    }

    public Utils.ColorProfile getProfile() {
        return profile;
    }

    public String getError() {
        return error;
    }
}
