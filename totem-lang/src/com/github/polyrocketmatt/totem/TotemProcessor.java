package com.github.polyrocketmatt.totem;

import com.github.polyrocketmatt.totem.exception.TotemException;
import com.github.polyrocketmatt.totem.lexical.TokenStream;
import com.github.polyrocketmatt.totem.lexical.Tokenizer;

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
        if (performLexicalAnalysis) {
            Tokenizer tokenizer = new Tokenizer(source);

            tokenizer.process();

            TokenStream stream = tokenizer.getStream();
            //  System.out.println(stream.toString());
        }
    }

}
