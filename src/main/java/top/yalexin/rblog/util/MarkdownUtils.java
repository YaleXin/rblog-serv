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
            if (node instanceof Emphasis){
                String aClass = attributes.get("class");
                if (aClass != null && !aClass.equals("")) {
                    aClass = aClass + " m-em";
                } else {
                    aClass = "m-em";
                }
                attributes.put("class", aClass);
            }
            // 粗体
            if (node instanceof StrongEmphasis){
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
        String blog1 = "\n" +
                "在`java`静态方法上面加`synchronized`的时候，是把当前类的`Class`类对象进行持锁。例如下面的例子：\n" +
                "\n" +
                "`Service.java`：分别提供两个静态方法和一个非静态方法<!--more-->\n" +
                "\n" +
                "```java\n" +
                "package com.yalexin.thread.demo03;\n" +
                "\n" +
                "/**\n" +
                " * Author：Yalexin\n" +
                " * Email： 181303209@yzu.edu.cn\n" +
                " */\n" +
                "public class Service {\n" +
                "    synchronized public static void printA() {\n" +
                "        System.out.println(\"thread name = \" + Thread.currentThread().getName() + \" 在 \" +\n" +
                "                System.currentTimeMillis() + \" 进入 printA()\");\n" +
                "        try {\n" +
                "            Thread.sleep(2000);\n" +
                "        } catch (InterruptedException e) {\n" +
                "            e.printStackTrace();\n" +
                "        }\n" +
                "        System.out.println(\"thread name = \" + Thread.currentThread().getName() + \" 在 \" +\n" +
                "                System.currentTimeMillis() + \" 退出 printA()\");\n" +
                "    }\n" +
                "\n" +
                "    synchronized public static void printB() {\n" +
                "        System.out.println(\"thread name = \" + Thread.currentThread().getName() + \" 在 \" +\n" +
                "                System.currentTimeMillis() + \" 进入 printB()\");\n" +
                "        System.out.println(\"thread name = \" + Thread.currentThread().getName() + \" 在 \" +\n" +
                "                System.currentTimeMillis() + \" 退出 printB()\");\n" +
                "    }\n" +
                "\n" +
                "    synchronized public void printC() {\n" +
                "        System.out.println(\"thread name = \" + Thread.currentThread().getName() + \" 在 \" +\n" +
                "                System.currentTimeMillis() + \" 进入 printC()\");\n" +
                "        System.out.println(\"thread name = \" + Thread.currentThread().getName() + \" 在 \" +\n" +
                "                System.currentTimeMillis() + \" 退出 printC()\");\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "```\n" +
                "\n" +
                "线程A调用静态方法A\n" +
                "\n" +
                "```java\n" +
                "package com.yalexin.thread.demo03;\n" +
                "\n" +
                "/**\n" +
                " * Author：Yalexin\n" +
                " * Email： 181303209@yzu.edu.cn\n" +
                " */\n" +
                "public class ThreadA extends Thread{\n" +
                "    public ThreadA(Service service) {\n" +
                "        this.service = service;\n" +
                "    }\n" +
                "\n" +
                "    private Service service;\n" +
                "    @Override\n" +
                "    public void run() {\n" +
                "        service.printA();\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "```\n" +
                "\n" +
                "线程B调用静态方法B\n" +
                "\n" +
                "```java\n" +
                "package com.yalexin.thread.demo03;\n" +
                "\n" +
                "/**\n" +
                " * Author：Yalexin\n" +
                " * Email： 181303209@yzu.edu.cn\n" +
                " */\n" +
                "public class ThreadB extends Thread{\n" +
                "    public ThreadB(Service service) {\n" +
                "        this.service = service;\n" +
                "    }\n" +
                "\n" +
                "    private Service service;\n" +
                "    @Override\n" +
                "    public void run() {\n" +
                "        service.printB();\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "```\n" +
                "\n" +
                "进程C调用非静态方法C\n" +
                "\n" +
                "```java\n" +
                "package com.yalexin.thread.demo03;\n" +
                "\n" +
                "/**\n" +
                " * Author：Yalexin\n" +
                " * Email： 181303209@yzu.edu.cn\n" +
                " */\n" +
                "public class ThreadC extends Thread{\n" +
                "    public ThreadC(Service service) {\n" +
                "        this.service = service;\n" +
                "    }\n" +
                "\n" +
                "    private Service service;\n" +
                "    @Override\n" +
                "    public void run() {\n" +
                "        service.printC();\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "```\n" +
                "\n" +
                "输出：\n" +
                "\n" +
                "```powershell\n" +
                "thread name = AAAA 在 1603940400153 进入 printA()\n" +
                "thread name = CCCC 在 1603940400153 进入 printC()\n" +
                "thread name = CCCC 在 1603940400153 退出 printC()\n" +
                "thread name = AAAA 在 1603940402169 退出 printA()\n" +
                "thread name = BBBB 在 1603940402169 进入 printB()\n" +
                "thread name = BBBB 在 1603940402169 退出 printB()\n" +
                "```\n" +
                "\n" +
                "可以看到\n" +
                "\n" +
                "A方法和B方法是同步的，是因为`synchronized`关键字加到静态方法上面的时候，是将`Service`类的`Class`类对象作为锁，而C方法和A或者B方法是异步的，是因为`synchronized`关键字加到非静态方法上面的时候，是将`Service`类的实例化对象作为锁，即C方法的锁和A或B的锁是两个不同的锁，二者可以异步进行。\n";

        System.out.println(markdownToHtmlExtensions(blog1));
    }



}
