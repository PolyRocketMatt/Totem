package com.github.polyrocketmatt.totem.lang.translator;

import com.github.polyrocketmatt.totem.lang.lexical.TokenType;
import com.github.polyrocketmatt.totem.lang.node.MethodNode;
import com.github.polyrocketmatt.totem.lang.node.Node;
import com.github.polyrocketmatt.totem.lang.node.ObjectNode;
import com.github.polyrocketmatt.totem.lang.utils.IdentityResolver;
import com.github.polyrocketmatt.totem.lang.utils.Parameter;

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

    /** The original translator */
    private final TotemTranslator translator;

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
    public ObjectTranslator(TotemTranslator translator, ObjectNode node, IdentityResolver resolver) {
        this.translator = translator;
        this.node = node;
        this.resolver = resolver;
    }

    /**
     * Translate an object.
     *
     */
    @Override
    public String translate(String indent) {
        StringBuilder builder = new StringBuilder();
        List<Parameter> parameters = node.getParameters();

        builder.append("struct ").append(node.getName()).append(" {").append("\n");

        indent = "    ";

        //   Build parameter fields
        for (Parameter parameter : parameters) {
            builder.append(indent).append(TypeResolver.getCPPType(parameter.getType()))
                    .append(" ").append(parameter.getName());

            //   TODO: Fix this ugly "100"
            if (parameter.getType().equals(TokenType.STRING))
                builder.append("[").append(100).append("]");

            builder.append(";").append("\n");
        }

        //   Build constructor
        builder.append("\n").append(indent).append(node.getName()).append("(");

        Map<Parameter, String> identityMap = new HashMap<>();

        int index = 0;
        for (Parameter parameter : parameters) {
            String identity = resolver.nextString();

            identityMap.put(parameter, identity);
            builder.append(TypeResolver.getCPPType(parameter.getType()));

            if (parameter.getType().equals(TokenType.STRING))
                builder.append("*");

            builder.append(" ").append(identity);

            if (index != parameters.size() - 1)
                builder.append(", ");

            index++;
        }

        builder.append(")").append(" ").append("{").append("\n");

        indent = "        ";

        for (Parameter parameter : parameters) {
            String identity = identityMap.get(parameter);

            builder.append(indent).append(parameter.getName()).append(" = ").append(identity).append(";").append("\n");
        }

        indent = "    ";
        builder.append(indent).append("}").append("\n").append("\n");

        //  Implement body
        for (Node subNode : node.getSubNodes()) {
            if (subNode instanceof MethodNode)
                builder.append(indent).append(new MethodTranslator(translator, (MethodNode) subNode, resolver).translate(indent));
        }

        indent = "";
        builder.append(indent).append("}").append("\n").append("\n");

        return builder.toString();
    }
}
