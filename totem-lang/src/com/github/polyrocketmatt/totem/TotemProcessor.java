package com.github.polyrocketmatt.totem;

/**
 * Created by PolyRocketMatt on 28/09/2020.
 */

public class TotemProcessor {

    private final String source;
    private boolean performLexicalAnalysis;
    private boolean performSyntacticAnalysis;
    private boolean performTranslation;
    private boolean isOut;

    public TotemProcessor(String source) {
        this.source = source;
    }

    public void init(boolean performLexicalAnalysis, boolean performSyntacticAnalysis, boolean performTranslation,
                     boolean isOut) {
        this.performLexicalAnalysis = performLexicalAnalysis;
        this.performSyntacticAnalysis = performSyntacticAnalysis;
        this.performTranslation = performTranslation;
        this.isOut = isOut;
    }

    public void process() {

    }

}
