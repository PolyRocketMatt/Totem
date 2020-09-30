package com.github.polyrocketmatt.totem.translator;

import com.github.polyrocketmatt.totem.lexical.TokenType;

/**
 * Created by PolyRocketMatt on 30/09/2020.
 *
 * Resolver for types.
 */

public class TypeResolver {

    /**
     * Resolve a Totem-type for a CPP-type.
     *
     * @param type the Totem-type
     * @return the CPP-type
     */
    public static String getCPPType(TokenType type) {
        switch (type) {
            case BOOL:
                return "bool";
            case FLOAT:
                return "float";
            case INT:
                return "int";
            case STRING:
                return "char";
            case VOID:
                return "void";
        }

        return "";
    }

}
