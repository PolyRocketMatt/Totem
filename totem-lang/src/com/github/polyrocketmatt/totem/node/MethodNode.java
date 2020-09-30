package com.github.polyrocketmatt.totem.node;

import com.github.polyrocketmatt.totem.lexical.TokenType;
import com.github.polyrocketmatt.totem.utils.Parameter;

import java.util.List;

/**
 * Created by PolyRocketMatt on 30/09/2020.
 *
 * Represents a method-node.
 */

public class MethodNode extends Node {

    private TokenType[] returnTypes;
    private String name;
    private List<Parameter> parameters;

    public MethodNode(ObjectNode object, TokenType[] returnTypes, String name, List<Parameter> parameters) {
        super(object);

        this.returnTypes = returnTypes;
        this.name = name;
        this.parameters = parameters;
    }

    /**
     * Get the return-types of the method.
     *
     * @return the return-types
     */
    public TokenType[] getReturnTypes() {
        return returnTypes;
    }

    /**
     * Get the name of the method.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Get a list of parameters for the method.
     *
     * @return the parameters
     */
    public List<Parameter> getParameters() {
        return parameters;
    }
}
