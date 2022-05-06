package top.yalexin.rblog.util.markdown;



import org.commonmark.parser.Parser.Builder;
import org.commonmark.Extension;
import org.commonmark.parser.Parser.ParserExtension;
import org.commonmark.renderer.NodeRenderer;
import org.commonmark.renderer.html.HtmlNodeRendererContext;
import org.commonmark.renderer.html.HtmlNodeRendererFactory;
import org.commonmark.renderer.html.HtmlRenderer.HtmlRendererExtension;
import org.commonmark.renderer.text.TextContentNodeRendererContext;
import org.commonmark.renderer.text.TextContentNodeRendererFactory;
import org.commonmark.renderer.text.TextContentRenderer.TextContentRendererExtension;

public class HighlightExtension implements ParserExtension, HtmlRendererExtension, TextContentRendererExtension {

    private HighlightExtension() {
    }

    public static Extension create() {
        return new HighlightExtension();
    }



    public void extend(Builder parserBuilder) {
        parserBuilder.customDelimiterProcessor(new HighlightDelimiterProcessor());
    }

    public void extend(org.commonmark.renderer.html.HtmlRenderer.Builder rendererBuilder) {
        rendererBuilder.nodeRendererFactory(new HtmlNodeRendererFactory() {
            public NodeRenderer create(HtmlNodeRendererContext context) {
                return new HighlightHtmlNodeRenderer(context);
            }
        });
    }

    public void extend(org.commonmark.renderer.text.TextContentRenderer.Builder rendererBuilder) {
        rendererBuilder.nodeRendererFactory(new TextContentNodeRendererFactory() {
            public NodeRenderer create(TextContentNodeRendererContext context) {
                return new HighlightTextContentNodeRenderer(context);
            }
        });
    }


}
