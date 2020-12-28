package com.github.polyrocketmatt.totem.parser;

import com.github.polyrocketmatt.totem.TotemPhase;
import com.github.polyrocketmatt.totem.exception.ParserException;
import com.github.polyrocketmatt.totem.exception.TotemException;
import com.github.polyrocketmatt.totem.lexical.Token;
import com.github.polyrocketmatt.totem.lexical.TokenStream;
import com.github.polyrocketmatt.totem.lexical.TokenType;
import com.github.polyrocketmatt.totem.node.ExpressionNode;
import com.github.polyrocketmatt.totem.node.Node;
import com.github.polyrocketmatt.totem.node.ParentNode;
import com.github.polyrocketmatt.totem.node.statements.ReturnStatementNode;
import com.github.polyrocketmatt.totem.node.statements.VariableDeclarationStatementNode;
import com.github.polyrocketmatt.totem.parser.parsers.*;
import com.github.polyrocketmatt.totem.utils.Prediction;

import java.text.MessageFormat;
import java.util.Arrays;

/**
 * Created by PolyRocketMatt on 01/10/2020.
 *
 * The parser to generate an AST.
 */

public class TotemParser implements TotemPhase {

    /** The stream of tokens to parse */
    private TokenStream stream;

    /** The parent-node of the parser */
    private ParentNode parentNode;

    /** The current super-node of the parser */
    private Node superNode;

    /** Parsers that are classified as acceptors */
    private Acceptor[] acceptors;

    /** Other parsers that do not accept */
    private AbstractParser<?>[] parsers;

    /** The current prediction for the parser */
    private Prediction prediction;

    /** The current block-level */
    private int blockLevel;

    /**
     * Initialize a new parser with the given stream
     * of tokens generated by the tokenizer.
     *
     * @param stream the stream of tokens
     */
    public TotemParser(TokenStream stream) {
        this.stream = stream;
        this.parentNode = new ParentNode();
        this.superNode = parentNode;

        this.acceptors = new Acceptor[] {
                new ObjectParser(this),
                new FunctionParser(this),
                new ReturnParser(this),
                new VariableDeclarationParser(this),
        };
        this.parsers = new AbstractParser<?>[] {
                new ExpressionParser(this)
        };

        this.prediction = null;
        this.blockLevel = 0;

        this.stream.setParser(this);
    }

    /**
     * Get the stream of tokens.
     *
     * @return the stream of tokens
     */
    public TokenStream getStream() {
        return stream;
    }

    /**
     * Get the parent-node.
     *
     * @return the parent-node
     */
    public ParentNode getParentNode() {
        return parentNode;
    }

    /**
     * Get the current super-node.
     *
     * @return the super-node
     */
    public Node getSuperNode() {
        return superNode;
    }

    /**
     * Set a new prediction for the parser.
     *
     * @param type the type of prediction
     */
    public void setPrediction(AbstractParser.NodeType type) {
        this.prediction = new Prediction(type);
    }

    @Override
    public void process() throws TotemException {
        parse();

        System.out.println("DONE");
    }

    /**
     * Get a specific type of parser.
     *
     * @param type the type returned by the parser
     * @return the specific parser if present
     */
    private AbstractParser<?> getSpecificParser(AbstractParser.NodeType type) {
        for (Acceptor acceptor : acceptors)
            if (((AbstractParser<?>) acceptor).getParsedType() == type)
                return (AbstractParser<?>) acceptor;
        for (AbstractParser<?> parser : parsers)
            if (parser.getParsedType() == type)
                return parser;
        return null;
    }

    /**
     * Parse the given stream of tokens into an AST.
     *
     * @throws ParserException if an exception occurs
     */
    private void parse() throws ParserException {
        Token token = stream.read();

        while (!stream.isAtEnd()) {
            if (token.getType() == TokenType.SEMI_COLON) {
                if (superNode.getNodeType() == AbstractParser.NodeType.PARENT_NODE)
                    throw new ParserException(this, MessageFormat.format("Unexpected semicolon at ({0},{1})!", token.getRow(), token.getColumn()));

                superNode = superNode.getSuperNode();
                stream.skip(1);
                token = stream.read();

                continue;
            }
            else if (token.getType() == TokenType.OBRACE) {
                blockLevel++;
                stream.skip(1);
                token = stream.read();

                continue;
            }
            else if (token.getType() == TokenType.CBRACE) {
                //  We should reset the super-node
                superNode = superNode.getSuperNode();

                blockLevel--;
                stream.skip(1);
                token = stream.read();

                continue;
            }
            else if (prediction != null) {
                AbstractParser.NodeType type = prediction.getType();
                AbstractParser<?> parser = getSpecificParser(type);

                //  TODO: See where to remove the prediction!
                if (parser != null) {
                    if (parser instanceof Acceptor)
                        if (!((Acceptor) parser).accepts())
                            throw new ParserException(this, MessageFormat.format("Expected {0}, found {1} instead!", parser.getName(), token.getType().toString()));

                    Node node = parser.parse(superNode);

                    switch (node.getNodeType()) {
                        case PARENT_NODE:
                            throw new ParserException(this, "Cannot have multiple parents!");
                        case OBJECT_NODE:
                            superNode.add(node);
                            superNode = node;

                            break;
                        case EXPRESSION_NODE:
                            if (superNode instanceof VariableDeclarationStatementNode)
                                ((VariableDeclarationStatementNode) superNode).setExpression((ExpressionNode) node);
                            else if (superNode instanceof ReturnStatementNode)
                                ((ReturnStatementNode) superNode).setExpression((ExpressionNode) node);

                            superNode.add(node);
                            prediction = null;

                            break;
                        case FUNCTION_NODE:
                            superNode.add(node);
                            superNode = node;

                            break;
                        case RETURN_NODE:
                            superNode.add(node);
                            superNode = node;

                            setPrediction(AbstractParser.NodeType.EXPRESSION_NODE);

                            break;
                        case VARIABLE_DECLARATION_NODE:
                        case TUPLE_DECLARATION_NODE:
                            superNode.add(node);
                            superNode = node;

                            if (stream.read().getType() == TokenType.EQUAL) {
                                setPrediction(AbstractParser.NodeType.EXPRESSION_NODE);
                                stream.skip(1);
                            }

                            break;
                        default:
                            break;
                    }

                    token = stream.read();

                    continue;
                }
            }

            boolean accepted = false;
            for (Acceptor acceptor : acceptors) {
                if (acceptor.accepts()) {
                    AbstractParser<?> parser = (AbstractParser<?>) acceptor;

                    accepted = true;
                    if (Arrays.asList(parser.getAcceptedParents()).contains(superNode.getNodeType())) {
                        Node node = parser.parse(superNode);

                        switch (node.getNodeType()) {
                            case OBJECT_NODE:
                            case FUNCTION_NODE:
                                superNode.add(node);
                                superNode = node;

                                break;
                            case RETURN_NODE:
                                superNode.add(node);
                                superNode = node;

                                setPrediction(AbstractParser.NodeType.EXPRESSION_NODE);

                                break;
                            case VARIABLE_DECLARATION_NODE:
                            case TUPLE_DECLARATION_NODE:
                                superNode.add(node);
                                superNode = node;

                                if (stream.read().getType() == TokenType.EQUAL) {
                                    setPrediction(AbstractParser.NodeType.EXPRESSION_NODE);
                                    stream.skip(1);
                                }

                                break;
                            default:
                                break;
                        }

                        break;
                    } else
                        throw new ParserException(this, MessageFormat.format("Cannot add {0} to a(n) {1}!", parser.getParsedType().getName(), superNode.getNodeType().getName()));
                }
            }

            if (!accepted) {
                for (AbstractParser<?> parser : parsers) {
                    if (Arrays.asList(parser.getAcceptedParents()).contains(superNode.getNodeType())) {
                        Node node = parser.parse(superNode);

                        switch (node.getNodeType()) {
                            case EXPRESSION_NODE:
                                if (superNode instanceof VariableDeclarationStatementNode)
                                    ((VariableDeclarationStatementNode) superNode).setExpression((ExpressionNode) node);
                                else if (superNode instanceof ReturnStatementNode)
                                    ((ReturnStatementNode) superNode).setExpression((ExpressionNode) node);

                                superNode.add(node);
                                prediction = null;

                                break;
                        }

                        break;
                    }
                }
            }

            token = stream.read();
        }

        if (blockLevel != 0)
            throw new ParserException(this, MessageFormat.format("Expected {0}, found {1} instead!", TokenType.CBRACE.toString(), TokenType.EOF.toString()));
    }

    /**
     * Synchronize the parser to continue parsing when a syntax error
     * has occurred somewhere in a previous expression to avoid
     * cascading/ghost errors.
     */
    public void sync() {
        Token token = stream.read();

        while (!stream.isAtEnd()) {
            if (token.getType() == TokenType.SEMI_COLON)
                return;

            switch (token.getType()) {
                case TYPE:
                case DEF:
                case FOR:
                case WHILE:
                case REPEAT:
                case IF:
                case ELSE:
                case ELSE_IF:
                case RETURN:
                case PRINT:
                case BOOL:
                case FLOAT:
                case INT:
                case STRING:
                    return;
            }

            token = stream.pop();
        }
    }

}
