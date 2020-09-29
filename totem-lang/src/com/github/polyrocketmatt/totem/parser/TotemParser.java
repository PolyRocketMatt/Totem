package com.github.polyrocketmatt.totem.parser;

import com.github.polyrocketmatt.totem.TotemPhase;
import com.github.polyrocketmatt.totem.exception.ParserException;
import com.github.polyrocketmatt.totem.exception.TotemException;
import com.github.polyrocketmatt.totem.lexical.Token;
import com.github.polyrocketmatt.totem.lexical.TokenStream;
import com.github.polyrocketmatt.totem.lexical.TokenType;
import com.github.polyrocketmatt.totem.node.Node;
import com.github.polyrocketmatt.totem.node.ObjectNode;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

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

    /** The roots of the source AST (These should always be object-nodes) */
    private List<Node> roots;

    public TotemParser(TokenStream stream) {
        this.stream = stream;
        this.parsers = new AbstractParser[] {
            new ObjectParser(this)
        };
        this.roots = new ArrayList<>();
    }

    /**
     * Get the roots of the abstract syntax trees generated
     * for the source.
     *
     * @return the roots
     */
    public List<Node> getRoots() {
        return roots;
    }

    /**
     * Parse the given stream of tokens into an abstract syntax tree.
     *
     * @throws ParserException if an exception occurred during parsing
     */
    @Override
    public void process() throws ParserException {
        //  Create a super-node object
        Node superNode = null;

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
            } else if (token.getType().equals(TokenType.CBRACE)) {
                stream.pop();
                level--;
            }

            //  Try every parser on the current stream of tokens
            for (AbstractParser<?> parser : parsers)

                //  If a parser accepts the input, we can check
                //  which parser accepted and make according decisions
                if (parser.accepts(stream)) {

                    //  Object Parser Algorithm
                    if (parser instanceof ObjectParser) {
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

                        //  Remove the OBrace token since the parser doesn't need it
                        TokenStream[] objectStreams = stream.split(objectParserStreamIndex - 1);

                        //  TODO: Is this allowed?
                        this.stream = objectStreams[1];

                        ObjectNode objectNode = ((ObjectParser) parser).parse(objectStreams[0]);

                        //  Add the object to the roots
                        roots.add(objectNode);

                        //  Make the object the current super-node
                        superNode = objectNode;
                    } else {

                        //  We cannot have code outside of the objects
                        if (superNode == null)
                            throw new ParserException("Code cannot exist outside of objects!");

                        //  TODO: Don't make object inside other objects (yet, planned feature)
                    }
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
