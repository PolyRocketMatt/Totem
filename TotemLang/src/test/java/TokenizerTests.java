import com.github.polyrocketmatt.totem.exception.TokenizerException;
import com.github.polyrocketmatt.totem.lexical.TokenStream;
import com.github.polyrocketmatt.totem.lexical.TokenType;
import com.github.polyrocketmatt.totem.lexical.TotemTokenizer;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by PolyRocketMatt on 03/10/2020.
 */

public class TokenizerTests {

    @Test
    public void testInvalidTokenAt() {
        String source = "@";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        assertThrows(TokenizerException.class, tokenizer::process);
    }

    @Test
    public void testEmptyString() {
        String source = "";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(1, stream.size());
        assertEquals(TokenType.EOF, stream.pop().getType());
    }

    @Test
    public void testCommentString() {
        String source = "//  This is a comment";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(1, stream.size());
        assertEquals(TokenType.EOF, stream.pop().getType());
    }

    @Test
    public void testBoolLiteralTrue() {
        String source = "true";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.BOOL_LITERAL, stream.read().getType());
        assertEquals(true, stream.read().getValue().getValue());
    }

    @Test
    public void testBoolLiteralFalse() {
        String source = "false";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.BOOL_LITERAL, stream.read().getType());
        assertEquals(false, stream.read().getValue().getValue());
    }

    @Test
    public void testFloatLiteral() {
        String source = "14.6f";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.FLOAT_LITERAL, stream.read().getType());
        assertEquals(14.6f, stream.read().getValue().getValue());
    }

    @Test
    public void testIntLiteral() {
        String source = "12596";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.INT_LITERAL, stream.read().getType());
        assertEquals(12596, stream.read().getValue().getValue());
    }

    @Test
    public void testStringLiteral() {
        String source = "\"Hello, World!\"";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.STRING_LITERAL, stream.read().getType());
        assertEquals("\"Hello, World!\"", stream.read().getValue().getValue());
    }

    @Test
    public void testSymbolPlus() {
        String source = "+";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.PLUS, stream.read().getType());
        assertEquals("+", stream.read().getValue().getValue());
    }

    @Test
    public void testSymbolMinus() {
        String source = "-";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.MINUS, stream.read().getType());
        assertEquals("-", stream.read().getValue().getValue());
    }

    @Test
    public void testSymbolAsterisk() {
        String source = "*";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.ASTERISK, stream.read().getType());
        assertEquals("*", stream.read().getValue().getValue());
    }

    @Test
    public void testSymbolForwardSlash() {
        String source = "/";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.F_SLASH, stream.read().getType());
        assertEquals("/", stream.read().getValue().getValue());
    }

    @Test
    public void testSymbolModulo() {
        String source = "%";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.MODULO, stream.read().getType());
        assertEquals("%", stream.read().getValue().getValue());
    }

    @Test
    public void testSymbolHat() {
        String source = "^";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.HAT, stream.read().getType());
        assertEquals("^", stream.read().getValue().getValue());
    }

    @Test
    public void testSymbolTilde() {
        String source = "~";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.TILDE, stream.read().getType());
        assertEquals("~", stream.read().getValue().getValue());
    }

    @Test
    public void testSymbolOpenParenthesis() {
        String source = "(";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.OPAREN, stream.read().getType());
        assertEquals("(", stream.read().getValue().getValue());
    }

    @Test
    public void testSymbolCloseParenthesis() {
        String source = ")";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.CPAREN, stream.read().getType());
        assertEquals(")", stream.read().getValue().getValue());
    }

    @Test
    public void testSymbolOpenBrace() {
        String source = "{";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.OBRACE, stream.read().getType());
        assertEquals("{", stream.read().getValue().getValue());
    }

    @Test
    public void testSymbolCloseBrace() {
        String source = "}";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.CBRACE, stream.read().getType());
        assertEquals("}", stream.read().getValue().getValue());
    }

    @Test
    public void testSymbolOpenBracket() {
        String source = "[";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.OBRACKET, stream.read().getType());
        assertEquals("[", stream.read().getValue().getValue());
    }

    @Test
    public void testSymbolCloseBracket() {
        String source = "]";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.CBRACKET, stream.read().getType());
        assertEquals("]", stream.read().getValue().getValue());
    }

    @Test
    public void testSymbolEqual() {
        String source = "=";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.EQUAL, stream.read().getType());
        assertEquals("=", stream.read().getValue().getValue());
    }

    @Test
    public void testSymbolExclamation() {
        String source = "!";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.EXCLAMATION, stream.read().getType());
        assertEquals("!", stream.read().getValue().getValue());
    }

    @Test
    public void testSymbolLessThan() {
        String source = "<";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.LESS_THAN, stream.read().getType());
        assertEquals("<", stream.read().getValue().getValue());
    }

    @Test
    public void testSymbolGreaterThan() {
        String source = ">";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.GREATER_THAN, stream.read().getType());
        assertEquals(">", stream.read().getValue().getValue());
    }

    @Test
    public void testSymbolAmpersand() {
        String source = "&";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.AMPERSAND, stream.read().getType());
        assertEquals("&", stream.read().getValue().getValue());
    }

    @Test
    public void testSymbolPipe() {
        String source = "|";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.PIPE, stream.read().getType());
        assertEquals("|", stream.read().getValue().getValue());
    }

    @Test
    public void testSymbolColon() {
        String source = ":";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.COLON, stream.read().getType());
        assertEquals(":", stream.read().getValue().getValue());
    }

    @Test
    public void testSymbolSemiColon() {
        String source = ";";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.SEMI_COLON, stream.read().getType());
        assertEquals(";", stream.read().getValue().getValue());
    }

    @Test
    public void testSymbolDot() {
        String source = ".";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.DOT, stream.read().getType());
        assertEquals(".", stream.read().getValue().getValue());
    }

    @Test
    public void testSymbolComma() {
        String source = ",";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.COMMA, stream.read().getType());
        assertEquals(",", stream.read().getValue().getValue());
    }

    @Test
    public void testSymbolHash() {
        String source = "#";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.HASH, stream.read().getType());
        assertEquals("#", stream.read().getValue().getValue());
    }

    @Test
    public void testSymbolIncrement() {
        String source = "++";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.PRE_POST_INCREMENT, stream.read().getType());
        assertEquals("++", stream.read().getValue().getValue());
    }

    @Test
    public void testSymbolDecrement() {
        String source = "--";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.PRE_POST_DECREMENT, stream.read().getType());
        assertEquals("--", stream.read().getValue().getValue());
    }

    @Test
    public void testSymbolCompEquals() {
        String source = "==";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.EQUALS_EQUALS, stream.read().getType());
        assertEquals("==", stream.read().getValue().getValue());
    }

    @Test
    public void testSymbolCompLessEquals() {
        String source = "<=";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.LESS_EQUALS, stream.read().getType());
        assertEquals("<=", stream.read().getValue().getValue());
    }

    @Test
    public void testSymbolCompGreaterEquals() {
        String source = ">=";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.GREATER_EQUALS, stream.read().getType());
        assertEquals(">=", stream.read().getValue().getValue());
    }

    @Test
    public void testSymbolCompNotEquals() {
        String source = "!=";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.NOT_EQUALS, stream.read().getType());
        assertEquals("!=", stream.read().getValue().getValue());
    }

    @Test
    public void testSymbolPlusEquals() {
        String source = "+=";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.PLUS_EQUALS, stream.read().getType());
        assertEquals("+=", stream.read().getValue().getValue());
    }

    @Test
    public void testSymbolMinusEquals() {
        String source = "-=";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.MINUS_EQUALS, stream.read().getType());
        assertEquals("-=", stream.read().getValue().getValue());
    }

    @Test
    public void testSymbolAsteriskEquals() {
        String source = "*=";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.ASTERISK_EQUALS, stream.read().getType());
        assertEquals("*=", stream.read().getValue().getValue());
    }

    @Test
    public void testSymbolForwardSlashEquals() {
        String source = "/=";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.F_SLASH_EQUALS, stream.read().getType());
        assertEquals("/=", stream.read().getValue().getValue());
    }

    @Test
    public void testSymbolModuloEquals() {
        String source = "%=";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.MODULO_EQUALS, stream.read().getType());
        assertEquals("%=", stream.read().getValue().getValue());
    }

    @Test
    public void testSymbolAmpersandEquals() {
        String source = "&=";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.AND_EQUALS, stream.read().getType());
        assertEquals("&=", stream.read().getValue().getValue());
    }

    @Test
    public void testSymbolPipeEquals() {
        String source = "|=";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.OR_EQUALS, stream.read().getType());
        assertEquals("|=", stream.read().getValue().getValue());
    }

    @Test
    public void testSymbolLogicalAnd() {
        String source = "&&";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.DOUBLE_AMPERSAND, stream.read().getType());
        assertEquals("&&", stream.read().getValue().getValue());
    }

    @Test
    public void testSymbolLogicalOr() {
        String source = "||";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.DOUBLE_PIPE, stream.read().getType());
        assertEquals("||", stream.read().getValue().getValue());
    }

    @Test
    public void testSymbolDoubleAsterisk() {
        String source = "**";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.DOUBLE_ASTERISK, stream.read().getType());
        assertEquals("**", stream.read().getValue().getValue());
    }

    @Test
    public void testKeywordFloat() {
        String source = "float";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.FLOAT, stream.read().getType());
        assertEquals("float", stream.read().getValue().getValue());
    }

    @Test
    public void testKeywordInt() {
        String source = "int";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.INT, stream.read().getType());
        assertEquals("int", stream.read().getValue().getValue());
    }

    @Test
    public void testKeywordString() {
        String source = "string";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.STRING, stream.read().getType());
        assertEquals("string", stream.read().getValue().getValue());
    }

    @Test
    public void testKeywordBool() {
        String source = "bool";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.BOOL, stream.read().getType());
        assertEquals("bool", stream.read().getValue().getValue());
    }

    @Test
    public void testKeywordDef() {
        String source = "def";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.DEF, stream.read().getType());
        assertEquals("def", stream.read().getValue().getValue());
    }

    @Test
    public void testKeywordUse() {
        String source = "use";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.USE, stream.read().getType());
        assertEquals("use", stream.read().getValue().getValue());
    }

    @Test
    public void testKeywordNull() {
        String source = "null";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.NULL, stream.read().getType());
        assertEquals("null", stream.read().getValue().getValue());
    }

    @Test
    public void testKeywordReturn() {
        String source = "return";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.RETURN, stream.read().getType());
        assertEquals("return", stream.read().getValue().getValue());
    }

    @Test
    public void testKeywordFor() {
        String source = "for";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.FOR, stream.read().getType());
        assertEquals("for", stream.read().getValue().getValue());
    }

    @Test
    public void testKeywordWhile() {
        String source = "while";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.WHILE, stream.read().getType());
        assertEquals("while", stream.read().getValue().getValue());
    }

    @Test
    public void testKeywordIf() {
        String source = "if";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.IF, stream.read().getType());
        assertEquals("if", stream.read().getValue().getValue());
    }

    @Test
    public void testKeywordElse() {
        String source = "else";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.ELSE, stream.read().getType());
        assertEquals("else", stream.read().getValue().getValue());
    }

    @Test
    public void testKeywordElseIf() {
        String source = "elseif";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.ELSE_IF, stream.read().getType());
        assertEquals("elseif", stream.read().getValue().getValue());
    }

    @Test
    public void testKeywordTypeOf() {
        String source = "typeof";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.TYPE_OF, stream.read().getType());
        assertEquals("typeof", stream.read().getValue().getValue());
    }

    @Test
    public void testKeywordVoid() {
        String source = "void";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.VOID, stream.read().getType());
        assertEquals("void", stream.read().getValue().getValue());
    }

    @Test
    public void testKeywordPrint() {
        String source = "print";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.PRINT, stream.read().getType());
        assertEquals("print", stream.read().getValue().getValue());
    }

    @Test
    public void testKeywordRepeat() {
        String source = "repeat";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.REPEAT, stream.read().getType());
        assertEquals("repeat", stream.read().getValue().getValue());
    }

    @Test
    public void testIdentifierSingle() {
        String source = "a";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.IDENTIFIER, stream.read().getType());
        assertEquals("a", stream.read().getValue().getValue());
    }

    @Test
    public void testIdentifierMulti() {
        String source = "ident";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.IDENTIFIER, stream.read().getType());
        assertEquals("ident", stream.read().getValue().getValue());
    }

    @Test
    public void testIdentifierAlphaNumeric() {
        String source = "ident0123";
        TotemTokenizer tokenizer = new TotemTokenizer(source);

        tokenizer.process();

        TokenStream stream = tokenizer.getStream();

        assertEquals(2, stream.size());
        assertEquals(TokenType.IDENTIFIER, stream.read().getType());
        assertEquals("ident0123", stream.read().getValue().getValue());
    }

}
