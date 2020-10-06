package com.github.polyrocketmatt.totem.utils;

import com.github.polyrocketmatt.totem.parser.AbstractParser;

/**
 * Created by PolyRocketMatt on 05/10/2020.
 */

public class Prediction {

    private AbstractParser.NodeType type;

    public Prediction(AbstractParser.NodeType type) {
        this.type = type;
    }

    public AbstractParser.NodeType getType() {
        return type;
    }
}
