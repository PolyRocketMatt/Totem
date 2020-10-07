package com.github.polyrocketmatt.totem.node.expressions;

import com.github.polyrocketmatt.totem.exception.InterpreterException;
import com.github.polyrocketmatt.totem.interpreter.TotemInterpreter;
import com.github.polyrocketmatt.totem.node.ExpressionNode;
import com.github.polyrocketmatt.totem.node.Node;
import com.github.polyrocketmatt.totem.parser.AbstractParser;
import com.github.polyrocketmatt.totem.utils.representables.RepresentableValue;
import com.github.polyrocketmatt.totem.utils.Value;

/**
 * Created by PolyRocketMatt on 03/10/2020.
 *
 * Represents a literal expression.
 */

public class LiteralExpressionNode extends ExpressionNode {

    /** The literal value */
    private Value<?> literal;

    /**
     * Initialize a new LiteralExpressionNode.
     *
     * @param superNode the super-node
     * @param literal the literal value
     */
    public LiteralExpressionNode(Node superNode, Value<?> literal) {
        super(superNode);

        this.literal = literal;
    }

    @Override
    public AbstractParser.NodeType getNodeType() {
        return AbstractParser.NodeType.EXPRESSION_NODE;
    }

    @Override
    public void visit(TotemInterpreter interpreter) throws InterpreterException {
        interpreter.getRepresentableValues().push(new RepresentableValue(literal));
    }

    @Override
    public String string(String indent) {
        StringBuilder builder = new StringBuilder();

        builder.append(indent).append("NODE=LITERAL(").append(literal.getValue().toString()).append(")").append("\n");

        return builder.toString();
    }
}
