package com.github.polyrocketmatt.totem.translator;

import com.github.polyrocketmatt.totem.lexical.TokenType;
import com.github.polyrocketmatt.totem.node.MethodNode;
import com.github.polyrocketmatt.totem.node.Node;
import com.github.polyrocketmatt.totem.node.ObjectNode;
import com.github.polyrocketmatt.totem.utils.IdentityResolver;
import com.github.polyrocketmatt.totem.utils.Parameter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by PolyRocketMatt on 30/09/2020.
 *
 * Translate an object into C++ code. Currently, objects
 * are translated as structs. Their methods will be put into
 * their associated struct as well.
 */

public class ObjectTranslator extends AbstractTranslator {

    /** The object to translate */
    private ObjectNode node;

    /** Identity Resolver */
    private IdentityResolver resolver;

    /**
     * Initialize a new object-translator for a given object.
     *
     * @param node the object
     * @param resolver the resolver
     */
    public ObjectTranslator(ObjectNode node, IdentityResolver resolver) {
        this.node = node;
        this.resolver = resolver;
    }

    /**
     * Translate an object.
     *
     */
    @Override
    public String translate() {
        StringBuilder builder = new StringBuilder();
        List<Parameter> parameters = node.getParameters();

        builder.append("struct ").append(node.getName()).append(" {").append("\n");

        //   Build parameter fields
        for (Parameter parameter : parameters) {
            builder.append("    ").append(TypeResolver.getCPPType(parameter.getType()))
                    .append(" ").append(parameter.getName());

            //   TODO: Fix this ugly "100"
            if (parameter.getType().equals(TokenType.STRING))
                builder.append("[").append(100).append("]");

            builder.append(";").append("\n");
        }

        //   Build constructor
        builder.append("\n").append("    ").append(node.getName()).append("(");

        Map<Parameter, String> identityMap = new HashMap<>();

        int index = 0;
        for (Parameter parameter : parameters) {
            String identity = resolver.nextString();

            identityMap.put(parameter, identity);
            builder.append(TypeResolver.getCPPType(parameter.getType()))
                    .append(" ").append(identity);

            if (index != parameters.size() - 1)
                builder.append(", ");

            index++;
        }

        builder.append(")").append(" ").append("{").append("\n");

        for (Parameter parameter : parameters) {
            String identity = identityMap.get(parameter);

            builder.append("        ").append(parameter.getName()).append(" = ").append(identity).append(";").append("\n");
        }

        builder.append("    }").append("\n").append("\n");

        //  Implement body
        for (Node subNode : node.getSubNodes()) {
            if (subNode instanceof MethodNode)
                builder.append("    ").append(new MethodTranslator((MethodNode) subNode, resolver).translate());
        }

        builder.append("}").append("\n").append("\n");

        return builder.toString();
    }
}
