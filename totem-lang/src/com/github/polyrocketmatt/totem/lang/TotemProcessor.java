package com.github.polyrocketmatt.totem.lang;

import com.github.polyrocketmatt.totem.lang.exception.ParserException;
import com.github.polyrocketmatt.totem.lang.exception.TotemException;
import com.github.polyrocketmatt.totem.lang.exception.TranslatorException;
import com.github.polyrocketmatt.totem.lang.lexical.Token;
import com.github.polyrocketmatt.totem.lang.lexical.TokenStream;
import com.github.polyrocketmatt.totem.lang.lexical.TokenType;
import com.github.polyrocketmatt.totem.lang.lexical.TotemTokenizer;
import com.github.polyrocketmatt.totem.lang.node.ParentNode;
import com.github.polyrocketmatt.totem.lang.node.MethodNode;
import com.github.polyrocketmatt.totem.lang.node.Node;
import com.github.polyrocketmatt.totem.lang.parser.TotemParser;
import com.github.polyrocketmatt.totem.lang.translator.TotemTranslator;

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
            stream.close();

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
        }

        ParentNode parent = null;

        if (performSyntacticAnalysis) {
            if (stream == null)
                throw new ParserException("Stream of tokens cannot be null!");

            TotemParser parser = new TotemParser(stream);

            parser.process();
            parent = parser.getParentNode();
        }

        if (performTranslation) {
            //  Check if an entry point exists
            //  TODO: Implement proper diagnostics
            boolean entry = false;
            for (Node node : parent.getSubNodes()) {
                if (node instanceof MethodNode && ((MethodNode) node).getName().equalsIgnoreCase("main")) {
                    MethodNode main = (MethodNode) node;

                    if (main.getReturnTypes().length > 1 || !main.getReturnTypes()[0].equals(TokenType.VOID))
                        throw new ParserException("Entry-point should have return-type void!");
                    if (main.getParameters().size() != 0)
                        throw new ParserException("Entry-point shouldn't have method parameters");

                    if (!entry)
                        entry = true;
                    else
                        throw new TranslatorException("Multiple entry-points found!");
                }
            }

            if (!entry)
                throw new TranslatorException("Couldn't find an entry-point!");

            TotemTranslator translator = new TotemTranslator(parent);

            translator.process();
        }
    }

}
