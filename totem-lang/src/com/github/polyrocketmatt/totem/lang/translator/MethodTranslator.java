package com.github.polyrocketmatt.totem.lang.translator;

import com.github.polyrocketmatt.totem.lang.lexical.TokenType;
import com.github.polyrocketmatt.totem.lang.node.MethodNode;
import com.github.polyrocketmatt.totem.lang.utils.IdentityResolver;
import com.github.polyrocketmatt.totem.lang.utils.Parameter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by PolyRocketMatt on 30/09/2020.
 */

public class MethodTranslator extends AbstractTranslator {

    /** The original translator */
    private final TotemTranslator translator;

    /** The method to translate */
    private MethodNode node;

    /** Identity Resolver */
    private IdentityResolver resolver;

    /**
     * Initialize a new method-translator for a given method.
     *
     * @param node the object
     * @param resolver the resolver
     */
    public MethodTranslator(TotemTranslator translator, MethodNode node, IdentityResolver resolver) {
        this.translator = translator;
        this.node = node;
        this.resolver = resolver;
    }

    @Override
    public String translate(String indent) {
        StringBuilder builder = new StringBuilder();
        List<Parameter> parameters = node.getParameters();

        //  Make distinction between tuples
        if (node.getReturnTypes().length > 1) {
            //TODO: Implement tuples
            return "";
        } else {
            if (node.getReturnTypes().length == 0)
                builder.append("void ");
            else
                builder.append(TypeResolver.getCPPType(node.getReturnTypes()[0])).append(" ");

            builder.append(node.getName()).append("(");

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

            //  TODO: Implement function body

            builder.append(indent).append("}").append("\n").append("\n");

            return builder.toString();
        }
    }
}
