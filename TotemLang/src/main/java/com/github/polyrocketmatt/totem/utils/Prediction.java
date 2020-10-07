package com.github.polyrocketmatt.totem.utils;

import com.github.polyrocketmatt.totem.parser.AbstractParser;

/**
 * Created by PolyRocketMatt on 05/10/2020.
 *
 * A prediction can hold a node-type.
 */

public class Prediction {

    /** The predicted node-type*/
    private AbstractParser.NodeType type;

    /**
     * Initialize a new Prediction.
     *
     * @param type the node-type
     */
    public Prediction(AbstractParser.NodeType type) {
        this.type = type;
    }

    /**
     * Get the prediction.
     *
     * @return the node-type
     */
    public AbstractParser.NodeType getType() {
        return type;
    }
}
