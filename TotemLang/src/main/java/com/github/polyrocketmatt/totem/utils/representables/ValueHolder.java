package com.github.polyrocketmatt.totem.utils.representables;

import java.util.List;

/**
 * Created by PolyRocketMatt on 06/10/2020.
 *
 * A value-holder holds a list of variables.
 */

public interface ValueHolder {

    /**
     * Get the variables of the holder.
     *
     * @return the variables
     */
    List<RepresentableVariable> getVariables();

}
