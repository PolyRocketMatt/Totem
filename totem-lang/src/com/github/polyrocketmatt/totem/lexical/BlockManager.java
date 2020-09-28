package com.github.polyrocketmatt.totem.lexical;

import java.util.LinkedList;
import java.util.regex.Pattern;

/**
 * Created by PolyRocketMatt on 29/09/2020.
 *
 * The block-manager is responsible for chopping
 * the original source into so called "blocks" of
 * code. These blocks can then be tokenized on separate
 * threads to increase performance. The block-manager is
 * also responsible for linking back all the created
 * streams into one TokenStream.
 */

public class BlockManager {

    //TODO: Fix source-files that are too big!
    /** The complete original source */
    private final String source;

    /** All the regex-matchable patterns */
    private final LinkedList<TokenData> patterns;

    /**
     * Initialize the block-manager and generate all pattern-data.
     *
     * @param source the original non-processed source.
     */
    public BlockManager(String source) {
        this.source = source;
        this.patterns = new LinkedList<>();

        add(TokenType.FLOAT_LITERAL, "\\d+.\\d+");
        add(TokenType.INT_LITERAL, "\\d+");
        add(TokenType.STRING_LITERAL,"\"(.*)?\"");
        add(TokenType.BOOL_LITERAL, "true|false");

        add(TokenType.PLUS, "[+]");
        add(TokenType.MINUS, "[-]");
        add(TokenType.ASTERISK, "[*]");
        add(TokenType.F_SLASH, "[/]");
        add(TokenType.MODULO, "[%]");
        add(TokenType.HAT, "\\^");
        add(TokenType.TILDE, "[~]");

        add(TokenType.OPAREN, "\\(");
        add(TokenType.CPAREN, "\\)");
        add(TokenType.OBRACE, "\\{");
        add(TokenType.CBRACE, "\\}");
        add(TokenType.OBRACKET, "\\[");
        add(TokenType.CBRACKET, "\\]");

        add(TokenType.EQUAL, "\\=");
        add(TokenType.EXCLAMATION, "\\!");
        add(TokenType.GREATER_THAN, "\\>");
        add(TokenType.LESS_THAN, "\\<");
        add(TokenType.AMPERSAND, "\\&");
        add(TokenType.COLON, "\\:");
        add(TokenType.SEMI_COLON, "\\;");
        add(TokenType.DOT, "\\.");
        add(TokenType.COMMA, "\\,");
        add(TokenType.HASH, "\\#");

        add(TokenType.FLOAT, "float");
        add(TokenType.INT, "int");
        add(TokenType.STRING, "string");
        add(TokenType.BOOL, "bool");
        add(TokenType.DEF, "def");
        add(TokenType.USE, "use");
        add(TokenType.NULL, "null");
        add(TokenType.RETURN, "return");
        add(TokenType.FOR, "for");
        add(TokenType.WHILE, "while");
        add(TokenType.IF, "if");
        add(TokenType.ELSE, "else");
        add(TokenType.ELSE_IF, "elseif");
        add(TokenType.TYPE_OF, "typeof");
        add(TokenType.VOID, "void");
        add(TokenType.PRINT, "print");
        add(TokenType.REPEAT, "repeat");

        add(TokenType.IDENTIFIER, "[a-zA-Z]([a-zA-Z0-9]*)?");
    }

    /**
     * Add a pattern, matched with a type, to the pattern-data.
     *
     * @param type the associated type
     * @param pattern the pattern
     */
    private void add(TokenType type, String pattern) {
        patterns.add(new TokenData(type, Pattern.compile("^(" + pattern +")")));
    }

    /**
     * Process the source.
     */
    private void process() {

    }

}
