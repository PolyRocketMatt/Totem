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

    @Override
    public void process() throws TotemException {
        /** The current parent node */
        Node superNode = null;

        while (!stream.read().getType().equals(TokenType.EOF)) {
            for (AbstractParser<?> parser : parsers)
                if (parser.accepts(stream)) {
                    if (parser instanceof ObjectParser) {
                        int objectParserStreamIndex = 3;
                        int parenthesisCount = 1;

                        while (parenthesisCount != 0) {
                            Token offsetToken = stream.peek(objectParserStreamIndex);

                            if (offsetToken.getType().equals(TokenType.OBRACE))
                                throw new ParserException(MessageFormat.format(
                                        "Expected \")\" at ({0}, {1}), found \"{\"",
                                        offsetToken.getColumn(),
                                        offsetToken.getColumn()
                                ));
                            else if (offsetToken.getType().equals(TokenType.OPAREN))
                                parenthesisCount++;
                            else if (offsetToken.getType().equals(TokenType.CPAREN))
                                parenthesisCount--;

                            objectParserStreamIndex++;
                        }

                        TokenStream[] objectStreams = stream.split(objectParserStreamIndex);

                        //  TODO: Is this allowed?
                        this.stream = objectStreams[1];

                        ObjectNode objectNode = ((ObjectParser) parser).parse(objectStreams[0]);

                        roots.add(objectNode);
                        superNode = objectNode;
                    } else {
                        if (superNode == null)
                            throw new ParserException("Code cannot exist outside of objects!");
                    }
                }
        }
    }

}
