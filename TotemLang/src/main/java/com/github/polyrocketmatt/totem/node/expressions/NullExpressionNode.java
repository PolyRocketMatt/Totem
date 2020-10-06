package com.github.polyrocketmatt.totem.node.expressions;

import com.github.polyrocketmatt.totem.exception.InterpreterException;
import com.github.polyrocketmatt.totem.interpreter.TotemInterpreter;
import com.github.polyrocketmatt.totem.node.ExpressionNode;
import com.github.polyrocketmatt.totem.node.Node;
import com.github.polyrocketmatt.totem.parser.AbstractParser;
import com.github.polyrocketmatt.totem.utils.RepresentableValue;
import com.github.polyrocketmatt.totem.utils.Value;

/**
 * Created by PolyRocketMatt on 05/10/2020.
 */

public class NullExpressionNode extends ExpressionNode {

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
