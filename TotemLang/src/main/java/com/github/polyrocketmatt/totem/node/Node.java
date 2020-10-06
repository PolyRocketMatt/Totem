package com.github.polyrocketmatt.totem.node;

import com.github.polyrocketmatt.totem.exception.InterpreterException;
import com.github.polyrocketmatt.totem.interpreter.TotemInterpreter;
import com.github.polyrocketmatt.totem.parser.AbstractParser;

import java.util.LinkedList;

/**
 * Created by PolyRocketMatt on 29/09/2020.
 *
 * Represents a node in the AST.
 */

public abstract class Node {

    /** The parent node of the current node */
    private Node superNode;

    /** The optional sub-nodes of the current node */
    private LinkedList<Node> subNodes;

    public Node(Node superNode, LinkedList<Node> nodes) {
        this.superNode = superNode;
        this.subNodes = nodes;
    }

    /**
     * Get the parent node of the current node.
     *
     * @return the super node
     */
    public Node getSuperNode() {
        return superNode;
    }

    /**
     * Set the parent node of the current node.
     *
     * @param superNode the super node
     */
    public void setSuperNode(Node superNode) {
        this.superNode = superNode;
    }

    /**
     * Get a list of sub-nodes this node is the parent of.
     *
     * @return a list of sub-nodes
     */
    public LinkedList<Node> getSubNodes() {
        return subNodes;
    }

    /**
     * Add a new sub-node
     *
     * @param node the sub-node
     */
    public void add(Node node) {
        if (node != null) {
            node.setSuperNode(this);
            subNodes.add(node);
        }
    }

    /**
     * Get the type of node.
     *
     * @return the type of node
     */
    public abstract AbstractParser.NodeType getNodeType();

    /**
     * Routine when a node is visited.
     *
     * @param interpreter the interpreter of the source
     */
    public abstract void visit(TotemInterpreter interpreter) throws InterpreterException;

    /**
     * Represent a node as a string.
     * TODO: This is debug only!
     *
     * @param indent the indentation used for representing the AST
     * @return the string representation of the node
     */
    public abstract String string(String indent);

}
