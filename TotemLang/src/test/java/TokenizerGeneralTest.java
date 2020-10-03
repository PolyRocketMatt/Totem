import com.github.polyrocketmatt.totem.exception.TokenizerException;
import com.github.polyrocketmatt.totem.lexical.TokenStream;
import com.github.polyrocketmatt.totem.lexical.TotemTokenizer;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * Created by PolyRocketMatt on 03/10/2020.
 */

public class TokenizerGeneralTest {

    @Test
    public void testFileCorrect() {
        try {
            String source = "C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\Totem\\tests\\tokenization_1.ttm";
            StringBuilder sourceBuilder = new StringBuilder();
            Files.lines(Paths.get(source), StandardCharsets.UTF_8).forEach(line -> sourceBuilder.append(line).append("\n"));

            source = sourceBuilder.toString();

            TotemTokenizer tokenizer = new TotemTokenizer(source);

            tokenizer.process();

            TokenStream stream = tokenizer.getStream();

            assertEquals(44, stream.size());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testFileCorrupt() {
        try {
            String source = "C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\Totem\\tests\\tokenization_2.ttm";
            StringBuilder sourceBuilder = new StringBuilder();
            Files.lines(Paths.get(source), StandardCharsets.UTF_8).forEach(line -> sourceBuilder.append(line).append("\n"));

            source = sourceBuilder.toString();

            TotemTokenizer tokenizer = new TotemTokenizer(source);

            assertThrows(TokenizerException.class, tokenizer::process);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
