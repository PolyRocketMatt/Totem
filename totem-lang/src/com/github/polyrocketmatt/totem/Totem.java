package com.github.polyrocketmatt.totem;

import com.github.polyrocketmatt.totem.exception.TotemException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Created by PolyRocketMatt on 28/09/2020.
 */

public class Totem {

    public static final String[] options = new String[] { "-el", "-ep", "-etp", "-o" };

    public static void main(String[] args) {
        try {
            if (args.length == 0)
                throw new TotemException(Utils.Phase.PRE_ANALYSIS, Utils.ColorProfile.ERROR,
                        "No input parameters given!");

            String path = args[0];
            StringBuilder sourceBuilder = new StringBuilder();
            Files.lines(Paths.get(path), StandardCharsets.UTF_8).forEach(sourceBuilder::append);
            String source = sourceBuilder.toString();

            boolean performLexicalAnalysis = true;
            boolean performSyntacticAnalysis = true;
            boolean performTranslation = true;
            boolean isOut = false;

            for (int i = 1; i < args.length; i++) {
                String option = args[i];

                if (Arrays.stream(options).noneMatch(predefinedOption -> predefinedOption.equalsIgnoreCase(option)))
                    throw new TotemException(Utils.Phase.PRE_ANALYSIS, Utils.ColorProfile.ERROR,
                            "No parameter \"" + option + "\" exists!");

                switch (option) {
                    case "-el":
                        performLexicalAnalysis = false;
                    case "-ep":
                        performSyntacticAnalysis = false;
                    case "-etp":
                        performTranslation = false;
                    case "-o":
                        isOut = true;
                    default:
                        break;
                }
            }

            TotemProcessor processor = new TotemProcessor(source);

            processor.init(performLexicalAnalysis, performSyntacticAnalysis, performTranslation, isOut);
            processor.process();
        } catch (Exception ex) {
            if (ex instanceof TotemException) {
                TotemException totemException = (TotemException) ex;
                String message = totemException.getProfile().getString(totemException.getError()) + " [Phase:" + totemException.getPhase().toString() + "]";
                System.out.println(message);
            } else
                ex.printStackTrace();
        }
    }

}
