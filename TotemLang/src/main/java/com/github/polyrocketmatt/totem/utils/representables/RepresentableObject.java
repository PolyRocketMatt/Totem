package com.github.polyrocketmatt.totem.utils.representables;

import com.github.polyrocketmatt.totem.utils.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PolyRocketMatt on 06/10/2020.
 *
 * Represents an object.
 */

public class RepresentableObject extends Representable implements ValueHolder {

    /** The name of the object */
    private String name;

    /** The global parameters of the object */
    private List<Parameter> parameters;

    /** The variables of the object */
    private List<RepresentableVariable> variables;

    /** The functions of the object */
    private List<RepresentableFunction> functions;

    /**
     * Initialize a new RepresentableObject.
     *
     * @param name the name
     * @param parameters the parameters
     */
    public RepresentableObject(String name, List<Parameter> parameters) {
        this.name = name;
        this.parameters = parameters;
        this.variables = new ArrayList<>();
        this.functions = new ArrayList<>();
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
     * Get the global parameters of the object.
     *
     * @return the parameters.
     */
    public List<Parameter> getParameters() {
        return parameters;
    }

    /**
     * Get the variables of the object.
     *
     * @return the variables
     */
    public List<RepresentableVariable> getVariables() {
        return variables;
    }

    /**
     * Get the functions of the object.
     *
     * @return the functions
     */
    public List<RepresentableFunction> getFunctions() {
        return functions;
    }

    @Override
    public String represent(String indent) {
        StringBuilder builder = new StringBuilder();

        builder.append(indent).append("Object -> <").append(name).append(">").append("\n");
        builder.append(indent).append("    ---PARAMETERS---").append("\n");

        if (parameters.size() > 0)
            for (Parameter parameter : parameters) {
                builder.append(indent).append("        ").append(parameter.getType().toString()).append(": ").append(parameter.getName());

                if (parameter.getValue() != null)
                    builder.append(": ").append(parameter.getValue().getValue().toString()).append("\n");
                else
                    builder.append(": undefined\n");
            }
        else
            builder.append(indent + "        ").append("none");

        builder.append("\n");
        builder.append(indent).append("    ---VARIABLES---").append("\n");

        if (variables.size() > 0)
            for (RepresentableVariable variable : variables)
                builder.append(variable.represent(indent + "        "));
        else
            builder.append(indent + "        ").append("none");

        builder.append("\n");
        builder.append(indent).append("    ---FUNCTIONS---").append("\n");

        if (functions.size() > 0)
            for (RepresentableFunction function : functions)
                builder.append(function.represent(indent + "        "));
        else
            builder.append(indent + "        ").append("none");

        return builder.toString();
    }
}
