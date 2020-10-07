package com.github.polyrocketmatt.totem.parser.parsers;

import com.github.polyrocketmatt.totem.exception.ParserException;
import com.github.polyrocketmatt.totem.lexical.Token;
import com.github.polyrocketmatt.totem.lexical.TokenType;
import com.github.polyrocketmatt.totem.node.Node;
import com.github.polyrocketmatt.totem.node.statements.VariableDeclarationStatementNode;
import com.github.polyrocketmatt.totem.parser.AbstractParser;
import com.github.polyrocketmatt.totem.parser.Acceptor;
import com.github.polyrocketmatt.totem.parser.TotemParser;
import com.github.polyrocketmatt.totem.utils.TypeUtils;

/**
 * Created by PolyRocketMatt on 05/10/2020.
 *
 * Parser to parse variable declarations.
 */

public class VariableDeclarationParser extends AbstractParser<VariableDeclarationStatementNode> implements Acceptor {

    /**
     * Initialize a new VariableDeclarationParser.
     *
     * @param parser the original parser
     */
    public VariableDeclarationParser(TotemParser parser) {
        super(parser, parser.getStream());
    }

    @Override
    public VariableDeclarationStatementNode parse(Node superNode) throws ParserException {
        Token type = getStream().matchOrThrow(TokenType.BOOL, TokenType.FLOAT, TokenType.INT, TokenType.STRING);
        Token identifier = getStream().matchOrThrow(TokenType.IDENTIFIER);

        return new VariableDeclarationStatementNode(superNode, type.getType(), identifier.getValue().getValue().toString());
    }

    @Override
    public NodeType[] getAcceptedParents() {
        return new NodeType[] { NodeType.PARENT_NODE, NodeType.OBJECT_NODE };
    }

    @Override
    public NodeType getParsedType() {
        return NodeType.VARIABLE_DECLARATION_NODE;
    }

    @Override
    public String getName() {
        return "variable";
    }

    @Override
    public boolean accepts() {
        return TypeUtils.isTypeToken(getStream().read().getType()) &&
                TypeUtils.isIdentifier(getStream().peek(1).getType()) &&
                (getStream().peek(2).getType() == TokenType.EQUAL || getStream().peek(2).getType() == TokenType.SEMI_COLON);
    }
}
