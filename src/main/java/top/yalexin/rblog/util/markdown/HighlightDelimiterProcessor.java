package top.yalexin.rblog.util.markdown;


import org.commonmark.node.Node;
import org.commonmark.node.Nodes;
import org.commonmark.node.SourceSpans;
import org.commonmark.node.Text;
import org.commonmark.parser.delimiter.DelimiterProcessor;
import org.commonmark.parser.delimiter.DelimiterRun;

import java.util.Iterator;

public class HighlightDelimiterProcessor implements DelimiterProcessor {
    public HighlightDelimiterProcessor() {
    }

    public char getOpeningCharacter() {
        return '=';
    }

    public char getClosingCharacter() {
        return '=';
    }

    public int getMinLength() {
        return 2;
    }

    public int process(DelimiterRun openingRun, DelimiterRun closingRun) {
        if (openingRun.length() >= 2 && closingRun.length() >= 2) {
            Text opener = openingRun.getOpener();
            Node highlight = new Highlight();
            SourceSpans sourceSpans = new SourceSpans();
            sourceSpans.addAllFrom(openingRun.getOpeners(2));
            Iterator var6 = Nodes.between(opener, closingRun.getCloser()).iterator();

            while(var6.hasNext()) {
                Node node = (Node)var6.next();
                highlight.appendChild(node);
                sourceSpans.addAll(node.getSourceSpans());
            }

            sourceSpans.addAllFrom(closingRun.getClosers(2));
            highlight.setSourceSpans(sourceSpans.getSourceSpans());
            opener.insertAfter(highlight);
            return 2;
        } else {
            return 0;
        }
    }
}
