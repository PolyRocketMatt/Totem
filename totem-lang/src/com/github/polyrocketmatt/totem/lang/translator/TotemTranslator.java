package com.github.polyrocketmatt.totem.lang.translator;

import com.github.polyrocketmatt.totem.lang.TotemPhase;
import com.github.polyrocketmatt.totem.lang.exception.ParserException;
import com.github.polyrocketmatt.totem.lang.exception.TotemException;
import com.github.polyrocketmatt.totem.lang.exception.TranslatorException;
import com.github.polyrocketmatt.totem.lang.node.ParentNode;
import com.github.polyrocketmatt.totem.lang.node.MethodNode;
import com.github.polyrocketmatt.totem.lang.node.Node;
import com.github.polyrocketmatt.totem.lang.node.ObjectNode;
import com.github.polyrocketmatt.totem.lang.utils.IdentityResolver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by PolyRocketMatt on 29/09/2020.
 *
 * Turn the AST generated by the parser into
 * C++ files. It will also keep a hold of every
 * object, method, variable created to check here
 * if the generated code will be valid code.
 */

public class TotemTranslator implements TotemPhase {

    /** The nodes of the current source */
    private List<Node> nodes;

    /** Keeps the entry method of the program */
    private MethodNode entry;

    /** The file that has to be written to */
    private File object;

    /**
     * Initialize a new translator.
     *
     * @param parent the parent node
     * @throws TotemException if a problem with IO has occurred
     */
    public TotemTranslator(ParentNode parent) throws TotemException {
        this.nodes = parent.getSubNodes();
        this.entry = null;
        try {
            this.object = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Totem\\translation.cpp");
            if (this.object.exists())
                this.object.delete();
            this.object.getParentFile().mkdirs();
            this.object.createNewFile();
        } catch (IOException ex) {
            throw new TranslatorException("Couldn't create new file!");
        }
    }

    /**
     * Get the entry-point of the program.
     *
     * @return the main method
     */
    public MethodNode getEntry() {
        return entry;
    }

    /**
     * Set the entry-point of the program.
     *
     * @param entry the main method
     */
    public void setEntry(MethodNode entry) {
        this.entry = entry;
    }

    /**
     * Translate the given AST into C++.
     *
     * @throws ParserException if an exception occurred during translation
     */
    @Override
    public void process() throws TotemException {
        try {
            IdentityResolver resolver = new IdentityResolver(7);
            FileWriter writer = new FileWriter(object, true);
            String entryIdentity = resolver.nextString();

            for (Node node : nodes) {
                String content = "";

                if (node instanceof ObjectNode)
                    content = new ObjectTranslator(this, (ObjectNode) node, resolver).translate("");
                else if (node instanceof MethodNode) {
                    if (((MethodNode) node).getName().equalsIgnoreCase("main")) {
                        ((MethodNode) node).setName(entryIdentity);
                        entry = (MethodNode) node;
                    }

                    content = new MethodTranslator(this, (MethodNode) node, resolver).translate("");
                }

                //   Write
                writer.write(content);
            }

            StringBuilder sourceEntry = new StringBuilder();

            sourceEntry.append("int main() {").append("\n");
            sourceEntry.append("    ").append(entryIdentity).append("();").append("\n");
            sourceEntry.append("}").append("\n");

            writer.write(sourceEntry.toString());
            writer.close();
        } catch (IOException ex) {
            //TODO: Proper error reporting using TotemException

            ex.printStackTrace();
        }
    }
}
