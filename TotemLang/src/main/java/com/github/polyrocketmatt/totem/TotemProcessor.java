package com.github.polyrocketmatt.totem;

import com.github.polyrocketmatt.totem.exception.ParserException;
import com.github.polyrocketmatt.totem.exception.TotemException;
import com.github.polyrocketmatt.totem.exception.InterpreterException;
import com.github.polyrocketmatt.totem.lexical.Token;
import com.github.polyrocketmatt.totem.lexical.TokenStream;
import com.github.polyrocketmatt.totem.lexical.TokenType;
import com.github.polyrocketmatt.totem.lexical.TotemTokenizer;
import com.github.polyrocketmatt.totem.node.ParentNode;
import com.github.polyrocketmatt.totem.node.Node;

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
            TotemTokenizer tokenizer = new TotemTokenizer(source);

            tokenizer.process();
            stream = tokenizer.getStream();

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

            System.out.println(stream.toString());
        }

        ParentNode parent = null;

        if (performSyntacticAnalysis) {
            if (stream == null)
                throw new ParserException("Stream of tokens cannot be null!");
        }
    }

}
