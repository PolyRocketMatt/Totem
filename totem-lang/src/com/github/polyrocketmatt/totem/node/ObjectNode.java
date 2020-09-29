package com.github.polyrocketmatt.totem.node;

import com.github.polyrocketmatt.totem.utils.Parameter;

import java.util.List;

/**
 * Created by PolyRocketMatt on 29/09/2020.
 *
 * Represents an object-node.
 */

public class ObjectNode extends Node {

    /** The name of the object */
    private String name;

    /** The global parameters of an object */
    private List<Parameter> parameters;

    /**
     * Initialize a new object-node.
     *
     * @param name the name of the object
     * @param parameters the global parameters of the object
     */
    public ObjectNode(String name, List<Parameter> parameters) {
        super(null);

        this.name = name;
        this.parameters = parameters;
    }

    /**
     * Get the name of the object.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Get a list of global parameters of the object.
     *
     * @return the parameters
     */
    public List<Parameter> getParameters() {
        return parameters;
    }
}
