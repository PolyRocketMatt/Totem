package com.github.polyrocketmatt.totem.node.statements;

import com.github.polyrocketmatt.totem.exception.InterpreterException;
import com.github.polyrocketmatt.totem.interpreter.TotemInterpreter;
import com.github.polyrocketmatt.totem.lexical.TokenType;
import com.github.polyrocketmatt.totem.node.ExpressionNode;
import com.github.polyrocketmatt.totem.node.Node;
import com.github.polyrocketmatt.totem.parser.AbstractParser;
import com.github.polyrocketmatt.totem.utils.Value;
import com.github.polyrocketmatt.totem.utils.representables.RepresentableValue;
import com.github.polyrocketmatt.totem.utils.representables.RepresentableVariable;

import java.util.LinkedList;

/**
 * Created by PolyRocketMatt on 05/10/2020.
 *
 * Represents a variable declaration.
 */

public class VariableDeclarationStatementNode extends Node {

    /** The token-type of the variable */
    private TokenType type;

    /** The name of the variable */
    private String name;

    /** The expression that is the value of the variable */
    private ExpressionNode expression;

    /**
     * Initialize a new VariableDeclarationStatementNode.
     *
     * @param superNode the super-node
     * @param type the token-type
     * @param name the name
     */
    public VariableDeclarationStatementNode(Node superNode, TokenType type, String name) {
        super(superNode, new LinkedList<>());

        this.type = type;
        this.name = name;
        this.expression = null;
    }

    /**
     * Get the expression that is the value of the variable.
     *
     * @return the expression
     */
    public ExpressionNode getExpression() {
        return expression;
    }

    /**
     * Set the expression that is the value of the variable.
     *
     * @param expression the expression
     */
    public void setExpression(ExpressionNode expression) {
        this.expression = expression;
    }

    @Override
    public AbstractParser.NodeType getNodeType() {
        return AbstractParser.NodeType.VARIABLE_DECLARATION_NODE;
    }

    @Override
    public void visit(TotemInterpreter interpreter) throws InterpreterException {
        RepresentableVariable variable;

        if (expression != null) {
            expression.visit(interpreter);

            RepresentableValue value = interpreter.getRepresentableValues().pop();

            variable = new RepresentableVariable(type, name, value);
        } else {
            RepresentableValue value = new RepresentableValue(new Value<>(null, null));

            variable = new RepresentableVariable(type, name, value);
        }

        interpreter.getHolder().getVariables().add(variable);
    }

    @Override
    public String string(String indent) {
        StringBuilder builder = new StringBuilder();

        builder.append(indent).append("NODE=VAR_DEC(").append(name).append(")").append("\n");

        for (Node subNode : getSubNodes())
            builder.append(subNode.string(indent + "    "));

        return builder.toString();
    }
}
