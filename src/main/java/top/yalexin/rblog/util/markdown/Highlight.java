package top.yalexin.rblog.util.markdown;

import org.commonmark.node.CustomNode;
import org.commonmark.node.Delimited;

public class Highlight extends CustomNode implements Delimited {
    private static final String DELIMITER = "==";

    public Highlight() {
    }

    public String getOpeningDelimiter() {
        return "==";
    }

    public String getClosingDelimiter() {
        return "==";
    }
}
