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

        add(TokenType.BOOL_LITERAL, "true|false");
        add(TokenType.FLOAT_LITERAL, "\\d+.\\d+f");
        add(TokenType.INT_LITERAL, "\\d+");
        add(TokenType.STRING_LITERAL,"\"(.*)?\"");

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
        add(TokenType.PIPE, "\\|");
        add(TokenType.COLON, "\\:");
        add(TokenType.SEMI_COLON, "\\;");
        add(TokenType.DOT, "\\.");
        add(TokenType.COMMA, "\\,");
        add(TokenType.HASH, "\\#");

        add(TokenType.FLOAT, "float");
        add(TokenType.INT, "int");
        add(TokenType.STRING, "string");
        add(TokenType.BOOL, "bool");
        add(TokenType.VAR, "var");
        add(TokenType.DEF, "def");
        add(TokenType.USE, "use");
        add(TokenType.NULL, "null");
        add(TokenType.RETURN, "return");
        add(TokenType.FOR, "for");
        add(TokenType.WHILE, "while");
        add(TokenType.IF, "if");
        add(TokenType.ELSE_IF, "elseif");
        add(TokenType.ELSE, "else");
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
        TokenStream singleStream = new TokenStream();


        int lineIndex = 1;
        for (String line : source.split("\n")) {
            line = line.trim();

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

                                    break;
                                case FLOAT_LITERAL:
                                    value = new Value<>(Float.valueOf(group), type);

                                    break;
                                case INT_LITERAL:
                                    value = new Value<>(Integer.valueOf(group), type);

                                    break;
                                case STRING_LITERAL:
                                    value = new Value<>(group, type);

                                    break;
                                case IDENTIFIER:
                                default:
                                    value = new Value<>(group.trim(), type);

                                    break;
                            }



                            Token token = new Token(value, data.getType(), lineIndex, length - remaining);

                            line = matcher.replaceFirst("").trim();
                            singleStream.add(token);
                            match = true;

                            break;
                        }
                    }

                    if (!match) {
                        throw new TokenizerException("Bad character input at ({0},{1}): {2}!", lineIndex, remaining, line);
                    }
                }
            }
        }

        singleStream.add(new Token(new Value<>("EOF", TokenType.EOF), TokenType.EOF, ++lineIndex, 0));

        postProcess(singleStream);
    }

    /**
     * Post process the tokenizer to replace single characters
     * with possible multi-character tokens.
     *
     * @param singleStream the stream generated from the source
     */
    private void postProcess(TokenStream singleStream) {
        this.stream = new TokenStream();

        while (singleStream.read() != null) {
            Token token = singleStream.read();

            switch (token.getType()) {
                case INT_LITERAL:
                    if (singleStream.peek(1).getType() == TokenType.DOT && singleStream.peek(2).getType() == TokenType.INT_LITERAL) {
                        String value = token.getValue().getValue().toString() + "." + singleStream.peek(2).getValue().getValue().toString();

                        stream.add(new Token(new Value<>(Float.parseFloat(value), TokenType.FLOAT_LITERAL), TokenType.FLOAT_LITERAL, token.getRow(), token.getColumn()));
                        singleStream.skip(3);
                    } else {
                        stream.add(token);
                        singleStream.skip(1);
                    }

                    break;
                case PLUS:
                    switch (singleStream.peek(1).getType()) {
                        case PLUS:
                            //  TODO: Change this to "Expressionable" of some type
                            if (stream.peek(2).getType() != null && (stream.read().getType() == TokenType.IDENTIFIER || singleStream.peek(2).getType() == TokenType.IDENTIFIER)) {
                                stream.add(new Token(new Value<>("++", TokenType.PRE_POST_INCREMENT), TokenType.PRE_POST_INCREMENT, token.getRow(), token.getColumn()));
                                singleStream.skip(2);
                            } else {
                                stream.add(token);
                                singleStream.skip(1);
                            }
                        case EQUAL:
                            stream.add(new Token(new Value<>("+=", TokenType.PLUS_EQUALS), TokenType.PLUS_EQUALS, token.getRow(), token.getColumn()));
                            singleStream.skip(2);

                            break;
                        default:
                            stream.add(token);
                            singleStream.skip(1);

                            break;
                    }

                    break;
                case MINUS:
                    switch (singleStream.peek(1).getType()) {
                        case MINUS:
                            //  TODO: Change this to "Expressionable" of some type
                            if (stream.peek(2) != null && (stream.read().getType() == TokenType.IDENTIFIER || singleStream.peek(2).getType() == TokenType.IDENTIFIER)) {
                                stream.add(new Token(new Value<>("--", TokenType.PRE_POST_DECREMENT), TokenType.PRE_POST_DECREMENT, token.getRow(), token.getColumn()));
                                singleStream.skip(2);
                            } else {
                                stream.add(token);
                                singleStream.skip(1);
                            }

                            break;
                        case EQUAL:
                            stream.add(new Token(new Value<>("-=", TokenType.MINUS_EQUALS), TokenType.MINUS_EQUALS, token.getRow(), token.getColumn()));
                            singleStream.skip(2);

                            break;
                        default:
                            stream.add(token);
                            singleStream.skip(1);

                            break;
                    }

                    break;
                case ASTERISK:
                    switch (singleStream.peek(1).getType()) {
                        case ASTERISK:
                            stream.add(new Token(new Value<>("**", TokenType.DOUBLE_ASTERISK), TokenType.DOUBLE_ASTERISK, token.getRow(), token.getColumn()));
                            singleStream.skip(2);

                            break;
                        case EQUAL:
                            stream.add(new Token(new Value<>("*=", TokenType.ASTERISK_EQUALS), TokenType.ASTERISK_EQUALS, token.getRow(), token.getColumn()));
                            singleStream.skip(2);

                            break;
                        default:
                            stream.add(token);
                            singleStream.skip(1);

                            break;
                    }

                    break;
                case F_SLASH:
                    if (singleStream.peek(1).getType() == TokenType.EQUAL) {
                        stream.add(new Token(new Value<>("/=", TokenType.F_SLASH_EQUALS), TokenType.F_SLASH_EQUALS, token.getRow(), token.getColumn()));
                        singleStream.skip(2);
                    } else {
                        stream.add(token);
                        singleStream.skip(1);
                    }

                    break;
                case MODULO:
                    if (singleStream.peek(1).getType() == TokenType.EQUAL) {
                        stream.add(new Token(new Value<>("%=", TokenType.MODULO_EQUALS), TokenType.MODULO_EQUALS, token.getRow(), token.getColumn()));
                        singleStream.skip(2);
                    } else {
                        stream.add(token);
                        singleStream.skip(1);
                    }

                    break;
                case EQUAL:
                    if (singleStream.peek(1).getType() == TokenType.EQUAL) {
                        stream.add(new Token(new Value<>("==", TokenType.EQUALS_EQUALS), TokenType.EQUALS_EQUALS, token.getRow(), token.getColumn()));
                        singleStream.skip(2);
                    } else {
                        stream.add(token);
                        singleStream.skip(1);
                    }

                    break;
                case EXCLAMATION:
                    if (singleStream.peek(1).getType() == TokenType.EQUAL) {
                        stream.add(new Token(new Value<>("!=", TokenType.NOT_EQUALS), TokenType.NOT_EQUALS, token.getRow(), token.getColumn()));
                        singleStream.skip(2);
                    } else {
                        stream.add(token);
                        singleStream.skip(1);
                    }

                    break;
                case AMPERSAND:
                    switch (singleStream.peek(1).getType()) {
                        case AMPERSAND:
                            stream.add(new Token(new Value<>("&&", TokenType.DOUBLE_AMPERSAND), TokenType.DOUBLE_AMPERSAND, token.getRow(), token.getColumn()));
                            singleStream.skip(2);

                            break;
                        case EQUAL:
                            stream.add(new Token(new Value<>("&=", TokenType.AND_EQUALS), TokenType.AND_EQUALS, token.getRow(), token.getColumn()));
                            singleStream.skip(2);

                            break;
                        default:
                            stream.add(token);
                            singleStream.skip(1);

                            break;
                    }

                    break;
                case PIPE:
                    switch (singleStream.peek(1).getType()) {
                        case PIPE:
                            stream.add(new Token(new Value<>("||", TokenType.DOUBLE_PIPE), TokenType.DOUBLE_PIPE, token.getRow(), token.getColumn()));
                            singleStream.skip(2);

                            break;
                        case EQUAL:
                            stream.add(new Token(new Value<>("|=", TokenType.OR_EQUALS), TokenType.OR_EQUALS, token.getRow(), token.getColumn()));
                            singleStream.skip(2);

                            break;
                        default:
                            stream.add(token);
                            singleStream.skip(1);

                            break;
                    }

                    break;
                case LESS_THAN:
                    if (singleStream.peek(1).getType() == TokenType.EQUAL) {
                        stream.add(new Token(new Value<>("<=", TokenType.LESS_EQUALS), TokenType.LESS_EQUALS, token.getRow(), token.getColumn()));
                        singleStream.skip(2);
                    } else {
                        stream.add(token);
                        singleStream.skip(1);
                    }

                    break;
                case GREATER_THAN:
                    if (singleStream.peek(1).getType() == TokenType.EQUAL) {
                        stream.add(new Token(new Value<>(">=", TokenType.GREATER_EQUALS), TokenType.GREATER_EQUALS, token.getRow(), token.getColumn()));
                        singleStream.skip(2);
                    } else {
                        stream.add(token);
                        singleStream.skip(1);
                    }

                    break;
                default:
                    stream.add(token);
                    singleStream.skip(1);

                    break;
            }
        }
    }

}
