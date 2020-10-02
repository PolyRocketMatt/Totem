package com.github.polyrocketmatt.totem.node;

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
        node.setSuperNode(this);
        subNodes.add(node);
    }

}
