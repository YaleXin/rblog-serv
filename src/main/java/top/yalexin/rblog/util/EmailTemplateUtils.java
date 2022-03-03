/**
 * Author: Yalexin
 * Email: me@yalexin.top
 **/
package top.yalexin.rblog.util;

/**
 * 邮件模板类
 * 原本想借助Thymeleaf，但是一直配置不起来
 */
public class EmailTemplateUtils {
    public static String toSelfHTML(String blogHost, String bloggernickname, String replyAddr, String blogTitle,
                                    String parentCommentNickname, String parentContent,
                                    String replyNickname, String replyContent) {

        String html =
                "<html >\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "<div style=\"border-radius: 10px 10px 10px 10px;font-size:13px;    color: #555555;width: 666px;font-family:'Century Gothic','Trebuchet MS','Hiragino Sans GB',微软雅黑,'Microsoft Yahei',Tahoma,Helvetica,Arial,'SimSun',sans-serif;margin:50px auto;border:1px solid #eee;max-width:100%;background: #ffffff repeating-linear-gradient(-45deg,#fff,#fff 1.125rem,transparent 1.125rem,transparent 2.25rem);box-shadow: 0 1px 5px rgba(0, 0, 0, 0.15);\">\n" +
                        "    <div style=\"width:100%;background:#49BDAD;color:#ffffff;border-radius: 10px 10px 0 0;background-image: -moz-linear-gradient(0deg, rgb(67, 198, 184), rgb(255, 209, 244));background-image: -webkit-linear-gradient(0deg, rgb(67, 198, 184), rgb(255, 209, 244));height: 66px;\">\n" +
                        "        <p style=\"font-size:15px;word-break:break-all;padding: 23px 32px;margin:0;background-color: hsla(0,0%,100%,.4);border-radius: 10px 10px 0 0;\">\n" +
                        "            您在【<span style=\"text-decoration:none;color: #ffffff;\"> " + bloggernickname + " </span>】上的文章有新回复啦！</p>\n" +
                        "    </div>\n" +
                        "    <div style=\"margin:40px auto;width:90%\">\n" +
                        "        <p><span >" + (parentCommentNickname != null ? parentCommentNickname : "") + "</span> 同学，曾在文章 《<a style=\"text-decoration:none; color:#12addb\" href = \"" + "http://" + replyAddr +"\" >" + blogTitle + "</a>》 上发表评论：</p>\n" +
                        "        <div \n" +
                        "             style=\"background: #fafafa repeating-linear-gradient(-45deg,#fff,#fff 1.125rem,transparent 1.125rem,transparent 2.25rem);box-shadow: 0 2px 5px rgba(0, 0, 0, 0.15);margin:20px 0px;padding:15px;border-radius:5px;font-size:14px;color:#555555;\">\n" +
                        "            " + (parentContent != null ? parentContent : "") + "\n" +
                        "        </div>\n" +
                        "        <p><span >" + replyNickname + "</span> 给Ta的回复如下：</p>\n" +
                        "        <div \n" +
                        "             style=\"background: #fafafa repeating-linear-gradient(-45deg,#fff,#fff 1.125rem,transparent 1.125rem,transparent 2.25rem);box-shadow: 0 2px 5px rgba(0, 0, 0, 0.15);margin:20px 0px;padding:15px;border-radius:5px;font-size:14px;color:#555555;\">\n" +
                        "            " + replyContent + "\n" +
                        "        </div>\n" +
                        "        <p>您可以点击\n" +
                        "            <a style=\"text-decoration:none; color:#12addb\" href=\"   " + "http://" + replyAddr + "\" tartget = \"_blank\">" + replyAddr + "</a>查看回复的完整內容，欢迎再次光临<a\n" +
                        "                style=\"text-decoration:none; color:#12addb\" href=\"  " + "http://" + blogHost + "\" tartget = \"_blank\">\n" +
                        "                " + bloggernickname + "的博客</a>。</p>\n" +
                        "        <style type=\"text/css\">\n" +
                        "            a:link{text-decoration:none}a:visited{text-decoration:none}a:hover{text-decoration:none}a:active{text-decoration:none}\n" +
                        "\n" +
                        "        </style>\n" +
                        "    </div>\n" +
                        "</div>\n" +
                        "</body>\n" +
                        "</html>";

        return html;
    }

    public static String toParentCmtHTML(String blogHost, String bloggernickname, String replyAddr, String blogTitle,
                                         String parentCommentNickname, String parentContent,
                                         String replyNickname, String replyContent) {

        String html =
                "<html >\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "<div style=\"border-radius: 10px 10px 10px 10px;font-size:13px;    color: #555555;width: 666px;font-family:'Century Gothic','Trebuchet MS','Hiragino Sans GB',微软雅黑,'Microsoft Yahei',Tahoma,Helvetica,Arial,'SimSun',sans-serif;margin:50px auto;border:1px solid #eee;max-width:100%;background: #ffffff repeating-linear-gradient(-45deg,#fff,#fff 1.125rem,transparent 1.125rem,transparent 2.25rem);box-shadow: 0 1px 5px rgba(0, 0, 0, 0.15);\">\n" +
                        "    <div style=\"width:100%;background:#49BDAD;color:#ffffff;border-radius: 10px 10px 0 0;background-image: -moz-linear-gradient(0deg, rgb(67, 198, 184), rgb(255, 209, 244));background-image: -webkit-linear-gradient(0deg, rgb(67, 198, 184), rgb(255, 209, 244));height: 66px;\">\n" +
                        "        <p style=\"font-size:15px;word-break:break-all;padding: 23px 32px;margin:0;background-color: hsla(0,0%,100%,.4);border-radius: 10px 10px 0 0;\">\n" +
                        "            您在【<span style=\"text-decoration:none;color: #ffffff;\"> " + bloggernickname + " </span>】上发表的的评论有新回复啦！</p>\n" +
                        "    </div>\n" +
                        "    <div style=\"margin:40px auto;width:90%\">\n" +
                        "        <p><span >" + (parentCommentNickname != null ? parentCommentNickname : "") + "</span> 同学，您曾在文章 《<a style=\"text-decoration:none; color:#12addb\" href=\" "+ "http://" + replyAddr +" \" >" + blogTitle + "</a>》 上发表评论：</p>\n" +
                        "        <div \n" +
                        "             style=\"background: #fafafa repeating-linear-gradient(-45deg,#fff,#fff 1.125rem,transparent 1.125rem,transparent 2.25rem);box-shadow: 0 2px 5px rgba(0, 0, 0, 0.15);margin:20px 0px;padding:15px;border-radius:5px;font-size:14px;color:#555555;\">\n" +
                        "            " + (parentContent != null ? parentContent : "") + "\n" +
                        "        </div>\n" +
                        "        <p><span >" + replyNickname + "</span> 给您的回复如下：</p>\n" +
                        "        <div \n" +
                        "             style=\"background: #fafafa repeating-linear-gradient(-45deg,#fff,#fff 1.125rem,transparent 1.125rem,transparent 2.25rem);box-shadow: 0 2px 5px rgba(0, 0, 0, 0.15);margin:20px 0px;padding:15px;border-radius:5px;font-size:14px;color:#555555;\">\n" +
                        "            " + replyContent + "\n" +
                        "        </div>\n" +
                        "        <p>您可以点击\n" +
                        "            <a style=\"text-decoration:none; color:#12addb\" href=\"   " + "http://" +  replyAddr + "\" tartget = \"_blank\">" + replyAddr + "</a>查看回复的完整內容，欢迎再次光临<a\n" +
                        "                style=\"text-decoration:none; color:#12addb\" href=\"  " +  "http://" + blogHost + "\" tartget = \"_blank\">\n" +
                        "                " + bloggernickname + "的博客</a>。</p>\n " +
                        "       <p>此邮件由博主 <span style=\"text-decoration:none; color:#12addb\" >" + bloggernickname + "</span> 自动发送。 如果您之前没有在该文章上进行评论，您的邮箱账户可能被恶意使用！对此我深感抱歉，请发送邮件告知于我，我将不会再发送给您\n" +
                        "        </p>" +
                        "        <style type=\"text/css\">\n" +
                        "            a:link{text-decoration:none}a:visited{text-decoration:none}a:hover{text-decoration:none}a:active{text-decoration:none}\n" +
                        "\n" +
                        "        </style>\n" +
                        "    </div>\n" +
                        "</div>\n" +
                        "</body>\n" +
                        "</html>";

        return html;
    }
}
