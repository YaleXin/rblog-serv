package top.yalexin.rblog.util.markdown;

import org.commonmark.node.Node;
import org.commonmark.renderer.text.TextContentNodeRendererContext;
import org.commonmark.renderer.text.TextContentWriter;


public class HighlightTextContentNodeRenderer extends HighlightNodeRenderer {

    private final TextContentNodeRendererContext context;
    private final TextContentWriter textContent;

    public HighlightTextContentNodeRenderer(TextContentNodeRendererContext context) {
        this.context = context;
        this.textContent = context.getWriter();
    }

    public void render(Node node){
        this.textContent.write('/');
        this.renderChildren(node);
        this.textContent.write('/');
    }

    private void renderChildren(Node parent) {
        Node next;
        for (Node node = parent.getFirstChild(); node != null; node = next){
            next = node.getNext();
            this.context.render(node);
        }
    }
}
