package com.github.polyrocketmatt.totem.lang.node;

import com.github.polyrocketmatt.totem.lang.lexical.TokenType;
import com.github.polyrocketmatt.totem.lang.utils.Parameter;

import java.util.LinkedList;
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

    public MethodNode(Node parent, TokenType[] returnTypes, String name, List<Parameter> parameters) {
        super(parent, new LinkedList<>());

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
     * Set the name of the method.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
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
