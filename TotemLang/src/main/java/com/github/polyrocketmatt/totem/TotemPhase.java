package com.github.polyrocketmatt.totem;

import com.github.polyrocketmatt.totem.exception.TotemException;

/**
 * Created by PolyRocketMatt on 29/09/2020.
 *
 * Represents a phase of the interpretation process.
 */

public interface TotemPhase {

    /**
     * Processing method.
     *
     * @throws TotemException if an exception occurs
     */
    void process() throws TotemException;

}
