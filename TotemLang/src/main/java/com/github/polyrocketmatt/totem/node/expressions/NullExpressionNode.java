package com.github.polyrocketmatt.totem.node.expressions;

import com.github.polyrocketmatt.totem.exception.InterpreterException;
import com.github.polyrocketmatt.totem.interpreter.TotemInterpreter;
import com.github.polyrocketmatt.totem.node.ExpressionNode;
import com.github.polyrocketmatt.totem.node.Node;
import com.github.polyrocketmatt.totem.parser.AbstractParser;
import com.github.polyrocketmatt.totem.utils.representables.RepresentableValue;
import com.github.polyrocketmatt.totem.utils.Value;

/**
 * Created by PolyRocketMatt on 05/10/2020.
 *
 * Represents a null expression.
 */

public class NullExpressionNode extends ExpressionNode {

    /**
     * Initialize a new NullExpression.
     *
     * @param superNode the super-node
     */
    public NullExpressionNode(Node superNode) {
        super(superNode);
    }

    @Override
    public AbstractParser.NodeType getNodeType() {
        return AbstractParser.NodeType.EXPRESSION_NODE;
    }

    @Override
    public void visit(TotemInterpreter interpreter) throws InterpreterException {
        interpreter.getRepresentableValues().push(new RepresentableValue(new Value<>(null, null)));
    }

    @Override
    public String string(String indent) {
        return "NODE=EXPR(NULL)\n";
    }
}
