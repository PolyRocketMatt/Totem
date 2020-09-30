package com.github.polyrocketmatt.totem.lang;

import com.github.polyrocketmatt.totem.lang.exception.TotemException;

/**
 * Created by PolyRocketMatt on 29/09/2020.
 */

public interface TotemPhase {

    void process() throws TotemException;

}
