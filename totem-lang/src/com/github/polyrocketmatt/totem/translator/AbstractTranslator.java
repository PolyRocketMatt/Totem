package com.github.polyrocketmatt.totem.translator;

import com.github.polyrocketmatt.totem.node.Node;

import java.io.File;

/**
 * Created by PolyRocketMatt on 30/09/2020.
 */

public abstract class AbstractTranslator<T extends Node> {

    public abstract void translate(File file);

}
