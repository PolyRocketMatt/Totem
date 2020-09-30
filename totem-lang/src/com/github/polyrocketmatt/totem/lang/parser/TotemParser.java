package com.github.polyrocketmatt.totem.lang.parser;

import com.github.polyrocketmatt.totem.lang.TotemPhase;
import com.github.polyrocketmatt.totem.lang.exception.ParserException;
import com.github.polyrocketmatt.totem.lang.lexical.Token;
import com.github.polyrocketmatt.totem.lang.lexical.TokenStream;
import com.github.polyrocketmatt.totem.lang.lexical.TokenType;
import com.github.polyrocketmatt.totem.lang.node.ParentNode;
import com.github.polyrocketmatt.totem.lang.node.MethodNode;
import com.github.polyrocketmatt.totem.lang.node.Node;
import com.github.polyrocketmatt.totem.lang.node.ObjectNode;

import java.text.MessageFormat;

/**
 * Created by PolyRocketMatt on 29/09/2020.
 *
 * The parser splits the original stream into
 * smaller parseable blocks and creates an AST.
 */

public class TotemParser implements TotemPhase {

    /** The stream that has to be parsed */
    private TokenStream stream;

    /** Types of parsers that can be used to parse the stream of tokens */
    private final AbstractParser<?>[] parsers;

    /** The parent-node */
    private ParentNode parentNode;

    /** The current super-node */
    private Node superNode;

    public TotemParser(TokenStream stream) {
        this.stream = stream;
        this.parsers = new AbstractParser[] {
                new ObjectParser(this),
                new MethodParser(this),
        };
        this.parentNode = new ParentNode();
        this.superNode = parentNode;
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
     * Parse the given stream of tokens into an abstract syntax tree.
     *
     * @throws ParserException if an exception occurred during parsing
     */
    @Override
    public void process() throws ParserException {
        //  Read the current token
        Token token = stream.read();

        //  Keep track of levels
        int level = 0;

        //  Parsing Algorithm
        while (!token.getType().equals(TokenType.EOF)) {
            //  Take care of leveling
            //  This isn't important anymore for the AST
            if (token.getType().equals(TokenType.OBRACE)) {
                stream.pop();
                level++;
                token = stream.read();

                continue;
            } else if (token.getType().equals(TokenType.CBRACE)) {
                stream.pop();
                level--;
                token = stream.read();
                superNode = superNode.getSuperNode();

                continue;
            }

            //  Try every parser on the current stream of tokens
            for (AbstractParser<?> parser : parsers)

                //  If a parser accepts the input, we can check
                //  which parser accepted and make according decisions
                if (parser.accepts(stream)) {

                    //  Object Parser Algorithm
                    if (parser instanceof ObjectParser) {
                        //  Super-node must be file-node
                        if (!(superNode instanceof ParentNode))
                            throw new ParserException("Cannot have nested objects!");

                        int objectParserStreamIndex = 3;
                        int parenthesisCount = 1;

                        while (parenthesisCount != 0) {
                            Token offsetToken = stream.peek(objectParserStreamIndex);

                            if (offsetToken.getType().equals(TokenType.OBRACE))
                                throw new ParserException(MessageFormat.format(
                                        "Expected ) at ({0}, {1}), found {",
                                        offsetToken.getColumn(),
                                        offsetToken.getColumn()
                                ));
                            else if (offsetToken.getType().equals(TokenType.OPAREN))
                                parenthesisCount++;
                            else if (offsetToken.getType().equals(TokenType.CPAREN))
                                parenthesisCount--;

                            objectParserStreamIndex++;
                        }

                        //  Remove the { token since the parser doesn't need it
                        TokenStream[] objectStreams = stream.split(objectParserStreamIndex - 1);

                        //  TODO: Is this allowed?
                        this.stream = objectStreams[1];

                        ObjectNode objectNode = ((ObjectParser) parser).parse(objectStreams[0]);

                        //  Add the object to the super-node
                        superNode.add(objectNode);

                        //  Make the object the current super-node
                        superNode = objectNode;
                    } else {
                        //  TODO: Don't make object inside other objects (yet, planned feature)
                        if (parser instanceof MethodParser) {
                            int parenthesisCount = 1;
                            int methodParserStreamReturnIndex = 1;

                            while (parenthesisCount != 0) {
                                Token offsetToken = stream.peek(methodParserStreamReturnIndex);

                                if (offsetToken.getType().equals(TokenType.OPAREN))
                                    parenthesisCount++;
                                else if (offsetToken.getType().equals(TokenType.CPAREN))
                                    parenthesisCount--;

                                methodParserStreamReturnIndex++;
                            }

                            methodParserStreamReturnIndex += 2;
                            parenthesisCount = 1;

                            while (parenthesisCount != 0) {
                                Token offsetToken = stream.peek(methodParserStreamReturnIndex);

                                if (offsetToken.getType().equals(TokenType.OPAREN))
                                    parenthesisCount++;
                                else if (offsetToken.getType().equals(TokenType.CPAREN))
                                    parenthesisCount--;

                                methodParserStreamReturnIndex++;
                            }

                            //  Remove the { token since the parser doesn't need it
                            TokenStream[] methodStreams = stream.split(methodParserStreamReturnIndex - 1);

                            //  TODO: Is this allowed?
                            this.stream = methodStreams[1];

                            MethodNode methodNode = ((MethodParser) parser).parse(methodStreams[0]);

                            superNode.add(methodNode);
                            superNode = methodNode;
                        }
                    }

                    break;
                }

            //  Read the next token in the stream
            token = stream.read();
        }

        //  Throw an exception if a leveling issue should have occurred
        //  This shouldn't be possible since the lexical analysis took care
        //  of this possibility
        //  TODO: Remove in release, since this is debug-only for the mentioned reason above
        if (level != 0)
            throw new ParserException("Unexpected EOF, expected \"}\"");
    }

}
