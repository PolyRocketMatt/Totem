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

}
