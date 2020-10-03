package com.github.polyrocketmatt.totem;

import com.github.polyrocketmatt.totem.exception.ParserException;
import com.github.polyrocketmatt.totem.exception.TokenizerException;
import com.github.polyrocketmatt.totem.exception.TotemException;
import com.github.polyrocketmatt.totem.exception.InterpreterException;
import com.github.polyrocketmatt.totem.interpreter.TotemInterpreter;
import com.github.polyrocketmatt.totem.lexical.Token;
import com.github.polyrocketmatt.totem.lexical.TokenStream;
import com.github.polyrocketmatt.totem.lexical.TokenType;
import com.github.polyrocketmatt.totem.lexical.TotemTokenizer;
import com.github.polyrocketmatt.totem.node.ParentNode;
import com.github.polyrocketmatt.totem.node.Node;
import com.github.polyrocketmatt.totem.parser.TotemParser;

/**
 * Created by PolyRocketMatt on 28/09/2020.
 *
 * This class is responsible for executing the
 * procedures to complete the user-specified tasks.
 */

public class TotemProcessor {

    /** The source that is being processed */
    private final String source;

    /** Perform lexical analysis */
    private boolean performLexicalAnalysis;

    /** Perform syntactic analysis */
    private boolean performSyntacticAnalysis;

    /** Perform translation to C-code */
    private boolean performTranslation;

    /** Print additional procedure information */
    private boolean isOut;

    /**
     * Initialize a new processor for the given source.
     * A new processor is created for every object-file
     * included (future).
     *
     * @param source the source that needs to be processed
     */
    public TotemProcessor(String source) {
        this.source = source;
    }

    /**
     * Initialize processor variables.
     *
     * @param performLexicalAnalysis perform the lexical analysis
     * @param performSyntacticAnalysis perform the syntactic analysis
     * @param performTranslation perform the translation to C-code
     * @param isOut print procedural information
     */
    public void init(boolean performLexicalAnalysis, boolean performSyntacticAnalysis, boolean performTranslation,
                     boolean isOut) {
        this.performLexicalAnalysis = performLexicalAnalysis;
        this.performSyntacticAnalysis = performSyntacticAnalysis;
        this.performTranslation = performTranslation;
        this.isOut = isOut;
    }

    /**
     * Start processing the provided source.
     */
    public void process() throws TotemException {
        TokenStream stream = null;

        if (performLexicalAnalysis) {
            try {
                TotemTokenizer tokenizer = new TotemTokenizer(source);

                tokenizer.process();
                stream = tokenizer.getStream();
            } catch (TokenizerException ex) { ex.printStackTrace(); }

            //  Check braces
            int count = 0;
            for (Token token : stream.getStream()) {
                switch (token.getType()) {
                    case OBRACE:
                        count++;

                        break;
                    case CBRACE:
                        count--;

                        break;
                }
            }

            if (count != 0)
                throw new ParserException("Unexpected EOF, expected \"}\"");

            if (isOut) {
                System.out.println(stream.toString());
                System.out.println("\n\n");
            }
        }

        ParentNode parent = null;

        if (performSyntacticAnalysis) {
            if (stream == null)
                throw new ParserException("Stream of tokens cannot be null!");

            try {
                TotemParser parser = new TotemParser(stream);

                parser.process();
                parent = parser.getParentNode();
            } catch (ParserException ex) { ex.printStackTrace(); }

            if (isOut)
                System.out.println(parent.string(""));
        }

        if (performTranslation) {
            if (parent == null)
                throw new InterpreterException("Cannot have an empty AST");

            try {
                TotemInterpreter interpreter = new TotemInterpreter(parent);

                interpreter.process();
            } catch (InterpreterException ex) { ex.printStackTrace(); }
        }
    }

}
