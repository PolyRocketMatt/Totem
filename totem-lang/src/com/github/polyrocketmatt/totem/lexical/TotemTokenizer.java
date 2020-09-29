package com.github.polyrocketmatt.totem.lexical;

import com.github.polyrocketmatt.totem.TotemPhase;
import com.github.polyrocketmatt.totem.exception.TokenizerException;
import com.github.polyrocketmatt.totem.utils.Value;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by PolyRocketMatt on 29/09/2020.
 *
 * The tokenizer is responsible for processing
 * it's received source into a new TokenStream.
 *
 * NOTE: A tokenizer might chop it's given source
 * further into even smaller chunks (e.g. if-statements)
 */

public class TotemTokenizer implements TotemPhase {

    /** The source that has to be tokenized */
    private final String source;

    /** The token-data that represents regex-matchable patterns*/
    private final LinkedList<TokenData> tokenData;

    /** The final stream of tokens */
    private TokenStream stream;

    public TotemTokenizer(String source) {
        this.source = source;
        this.tokenData = new LinkedList<>();

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
     * Associate a token-type with a regex-pattern as token-data.
     *
     * @param type the token-type
     * @param pattern the pattern
     */
    private void add(TokenType type, String pattern) {
        tokenData.add(new TokenData(type, Pattern.compile("^(" + pattern +")")));
    }

    /**
     * Get the final generated stream of tokens.
     *
     * @return the stream of tokens
     */
    public TokenStream getStream() {
        return stream;
    }

    /**
     * Process the original source into a stream of tokens.
     *
     * @throws TokenizerException if an unrecognized token has been found
     */
    @Override
    public void process() throws TokenizerException {
        this.stream = new TokenStream();


        int lineIndex = 1;
        for (String line : source.split("\n")) {
            if (!line.startsWith("//")) {
                line = line.trim();
                lineIndex++;
                int length = line.length();
                while (!line.isEmpty()) {
                    int remaining = line.length();
                    boolean match = false;

                    for (TokenData data : tokenData) {
                        Matcher matcher = data.getPattern().matcher(line);
                        if (matcher.find()) {
                            String group = matcher.group().trim();
                            Value<?> value;
                            TokenType type = data.getType();

                            switch (type) {
                                case BOOL_LITERAL:
                                    value = new Value<>(Boolean.valueOf(group), type);
                                case FLOAT_LITERAL:
                                    value = new Value<>(Float.valueOf(group), type);
                                case INT_LITERAL:
                                    value = new Value<>(Integer.valueOf(group), type);
                                case STRING_LITERAL:
                                    value = new Value<>(String.valueOf(group), type);
                                case IDENTIFIER:
                                default:
                                    value = new Value<>(group.trim(), type);
                            }

                            Token token = new Token(value, data.getType(), lineIndex, length - remaining);

                            line = matcher.replaceFirst("").trim();
                            stream.add(token);
                            match = true;

                            break;
                        }
                    }

                    if (!match) {
                        throw new TokenizerException("Bad character input at ({0},{1})!", lineIndex, remaining);
                    }
                }
            }
        }

        stream.add(new Token(new Value<>("EOF", TokenType.EOF), TokenType.EOF, ++lineIndex, 0));
    }

}
