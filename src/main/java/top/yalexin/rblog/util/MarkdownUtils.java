package top.yalexin.rblog.util;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;
import org.commonmark.renderer.html.AttributeProviderFactory;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.*;

public class MarkdownUtils {
    /**
     * markdown格式转换成HTML格式
     *
     * @param markdown
     * @return
     */
    public static String markdownToHtml(String markdown) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }

    /**
     * 增加扩展[标题锚点，表格生成]
     * Markdown转换成HTML
     *
     * @param markdown
     * @return
     */
    public static String markdownToHtmlExtensions(String markdown) {
        //h标题生成id
        Set<Extension> headingAnchorExtensions = Collections.singleton(HeadingAnchorExtension.create());
        //转换table的HTML
        List<Extension> tableExtension = Arrays.asList(TablesExtension.create());
        Parser parser = Parser.builder()
                .extensions(tableExtension)
                .build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder()
                .extensions(headingAnchorExtensions)
                .extensions(tableExtension)
                .attributeProviderFactory(new AttributeProviderFactory() {
                    public AttributeProvider create(AttributeProviderContext context) {
                        return new CustomAttributeProvider();
                    }
                })
                .build();
        return renderer.render(document);
    }

    /**
     * 处理标签的属性
     */
    static class CustomAttributeProvider implements AttributeProvider {
        @Override
        public void setAttributes(Node node, String tagName, Map<String, String> attributes) {
            //改变a标签的target属性为_blank
            if (node instanceof Link) {
                attributes.put("target", "_blank");
            }
            String name = node.getClass().getName();
//            if (node instanceof TableBlock) {
//                attributes.put("class", "ui celled table");
//            }
            // 代码块
            if (node instanceof FencedCodeBlock) {
                String aClass = attributes.get("class");
                if (aClass != null && !aClass.equals("")) {
                    aClass = aClass + " line-numbers";
                } else {
                    aClass = "line-numbers";
                }
                attributes.put("class", aClass);
            }
            // 行内代码
            if (node instanceof Code && node.getParent() instanceof Paragraph) {
                attributes.put("class", " language-markup");
            }
            if (node instanceof Image) {
                String aClass = attributes.get("class");
                if (aClass != null && !aClass.equals("")) {
                    aClass = aClass + " ui rounded image fancy-box-img";
                } else {
                    aClass = "ui rounded image fancy-box-img";
                }
                attributes.put("class", aClass);
            }
            // 斜体
            if (node instanceof Emphasis) {
                String aClass = attributes.get("class");
                if (aClass != null && !aClass.equals("")) {
                    aClass = aClass + " m-em";
                } else {
                    aClass = "m-em";
                }
                attributes.put("class", aClass);
            }
            // 粗体
            if (node instanceof StrongEmphasis) {
                String aClass = attributes.get("class");
                if (aClass != null && !aClass.equals("")) {
                    aClass = aClass + " strong";
                } else {
                    aClass = "strong";
                }
                attributes.put("class", aClass);
            }
        }
    }


    public static void main(String[] args) {
        String table = "|表头一|表头二|表头三|\n" +
                "|:---:|:---:|:---:|\n" +
                "|1|2|3|\n" +
                "|4|5|6|\n\n-----------\n" +
                "\n" +
                "header 1 | header 2\n" +
                "---|---\n" +
                "row 1 col 1 | row 1 col 2\n" +
                "row 2 col 1 | row 2 col 2\n" +
                "\n";
        String c__code = "```cpp\n" +
                "int main(){\n" +
                "    return 0;\n" +
                "}\n" +
                "```";
        String cCode = "```c++\n" +
                "int main(){\n" +
                "    return 0;\n" +
                "}\n" +
                "```";
        System.out.println(markdownToHtmlExtensions(c__code));
        System.out.println(markdownToHtmlExtensions(cCode));
    }
}
