package com.github.polyrocketmatt.totem.node;

import com.github.polyrocketmatt.totem.exception.InterpreterException;
import com.github.polyrocketmatt.totem.utils.RepresentableValue;

import java.util.LinkedList;

/**
 * Created by PolyRocketMatt on 01/10/2020.
 */

public abstract class ExpressionNode extends Node {

    public ExpressionNode(Node superNode) {
        super(superNode, new LinkedList<>());
    }

    public abstract RepresentableValue visit() throws InterpreterException;

}
