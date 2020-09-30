package com.github.polyrocketmatt.totem.lang.node;

import java.util.LinkedList;

/**
 * Created by PolyRocketMatt on 30/09/2020.
 */

public class ParentNode extends Node {

    private String parent = "parent";

    public ParentNode() {
        super(null, new LinkedList<>());
    }

}
