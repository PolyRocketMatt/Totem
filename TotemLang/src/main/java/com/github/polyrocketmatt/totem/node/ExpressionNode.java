package com.github.polyrocketmatt.totem.node;

import java.util.LinkedList;

/**
 * Created by PolyRocketMatt on 01/10/2020.
 *
 * A sub-abstraction to represent expressions.
 */

public abstract class ExpressionNode extends Node {

    public ExpressionNode(Node superNode) {
        super(superNode, new LinkedList<>());
    }

}
