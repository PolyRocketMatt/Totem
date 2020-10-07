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

    @Override
    public String represent(String indent) {
        StringBuilder builder = new StringBuilder();

        builder.append(indent).append("Object -> <").append(name).append(">").append("\n");
        builder.append(indent).append("    ---PARAMETERS---").append("\n");

        for (Parameter parameter : parameters) {
            builder.append(indent).append("        ").append(parameter.getType().toString()).append(": ").append(parameter.getName());

            if (parameter.getValue() != null)
                builder.append(": ").append(parameter.getValue().getValue().toString()).append("\n");
            else
                builder.append(": undefined\n");
        }

        builder.append("\n");
        builder.append(indent).append("    ---VARIABLES---").append("\n");

        for (RepresentableVariable variable : variables)
            builder.append(variable.represent(indent + "        "));

        return builder.toString();
    }
}
