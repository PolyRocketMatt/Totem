package com.github.polyrocketmatt.totem.utils.representables;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by PolyRocketMatt on 06/10/2020.
 *
 * Represents a parent.
 */

public class RepresentableParent extends Representable implements ValueHolder {

    /** List of variables */
    private List<RepresentableVariable> variables;

    /** List of objects */
    private List<RepresentableObject> objects;

    /** List of functions */
    private List<RepresentableFunction> functions;

    /**
     * Initialize a new RepresentableParent.
     */
    public RepresentableParent() {
        this.variables = new ArrayList<>();
        this.objects = new ArrayList<>();
        this.functions = new ArrayList<>();
    }

    /**
     * Get the list of variables.
     *
     * @return the list of variables
     */
    public List<RepresentableVariable> getVariables() {
        return variables;
    }

    /**
     * Get the list of objects.
     *
     * @return the list of objects
     */
    public List<RepresentableObject> getObjects() {
        return objects;
    }

    /**
     * Get the list of functions.
     *
     * @return the list of functions
     */
    public List<RepresentableFunction> getFunctions() {
        return functions;
    }

    @Override
    public String represent(String indent) {
        StringBuilder builder = new StringBuilder();

        builder.append(indent).append("Parent ->").append("\n");

        for (RepresentableVariable variable : variables)
            builder.append(variable.represent(indent + "    "));

        for (RepresentableObject object : objects)
            builder.append(object.represent(indent + "    "));

        for (RepresentableFunction function : functions)
            builder.append(function.represent(indent + "    "));

        return builder.toString();
    }
}
