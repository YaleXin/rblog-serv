package top.yalexin.rblog.util.markdown;

import org.commonmark.node.Node;
import org.commonmark.renderer.NodeRenderer;
import org.commonmark.renderer.html.HtmlNodeRendererContext;
import org.commonmark.renderer.html.HtmlWriter;

import java.util.Collections;
import java.util.Map;


public class HighlightHtmlNodeRenderer extends HighlightNodeRenderer implements NodeRenderer {

    private final HtmlNodeRendererContext context;
    private final HtmlWriter html;

    public HighlightHtmlNodeRenderer(HtmlNodeRendererContext context) {
        this.context = context;
        this.html = context.getWriter();
    }



    public void render(Node node) {
        Map<String, String> attributes = this.context.extendAttributes(node, "mark", Collections.emptyMap());
        this.html.tag("mark", attributes);
        this.renderChildren(node);
        this.html.tag("/mark");
    }

    private void renderChildren(Node parent) {
        Node next;
        for(Node node = parent.getFirstChild(); node != null; node = next) {
            next = node.getNext();
            this.context.render(node);
        }

    }
}
