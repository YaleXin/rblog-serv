/**
 * Author: Yalexin
 * Email: me@yalexin.top
 **/
package top.yalexin.rblog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import top.yalexin.rblog.entity.Blog;
import top.yalexin.rblog.entity.Comment;
import top.yalexin.rblog.util.EmailTemplateUtils;

@Component
@Service
@Transactional
public class SendEmailServiceImpl implements SendEmailService {

    @Autowired
    private JavaMailSenderImpl javaMailSender;
    @Autowired
    private CommentService commentService;

    @Value("${comment-email-enable}")
    boolean commentEmailEnable;

    @Value("${comment_email_self_enable}")
    boolean commentEmailSelfEnable;

    @Autowired
    private BlogServiceImpl blogService;


    @Value("${blog_host}")
    private String blog_host;

    @Value("${blogger_nickname}")
    private String blogger_nickname;

    @Value("${emailfrom}")
    private String email_from;

    @Value("${email_receive}")
    private String email_receive;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Transactional
    @Async
    @Override
    public void send(Comment parentComment, Comment replyComment, boolean is2Parent) {
        String parentCommentEmail = null;
        String parentCommentNickname = null;
        String parentCommentContent = null;
        String replyAddr = null;
        if (parentComment != null) {
            parentCommentEmail = parentComment.getEmail();
            parentCommentNickname = parentComment.getNickname();
            parentCommentContent = parentComment.getContent();
        }
        Blog blog = null;
        Long blogId = replyComment.getBlogId();
        // 假如是来自留言区的评论
        if (blogId == 0) {
            blog = new Blog();
            blog.setName("最近的吐槽(留言区)");
            replyAddr = "/talk";
        } else {
            blog = blogService.getBlogById(replyComment.getBlogId());
            replyAddr = "/blog/" + blog.getId();
        }
        // 如果是给父级评论发送邮件(commentService 已经判定改评论尚未发送邮件)
        if (is2Parent && commentEmailEnable) {
            String html = EmailTemplateUtils.toParentCmtHTML(blog_host, blogger_nickname, blog_host + replyAddr, blog.getName(), parentCommentNickname, parentCommentContent, replyComment.getNickname(), replyComment.getContent());
            boolean success = sendHtmlMail(email_from, parentCommentEmail, "您之前发表的评论有新的回复啦，赶快去看一下吧！", html);
            if (success)sent(replyComment.getId());
        } else if (commentEmailSelfEnable) {
            String html = EmailTemplateUtils.toSelfHTML(blog_host, blogger_nickname, blog_host + replyAddr, blog.getName(), parentCommentNickname, parentCommentContent, replyComment.getNickname(), replyComment.getContent());
            sendHtmlMail(email_from, email_receive, "您的博客有新评论啦，快去审核吧", html);
        }
    }

    private boolean sendHtmlMail(String from, String to, String subject, String content) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;
        boolean success = false;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);//true代表支持html
            javaMailSender.send(message);
            success = true;
        } catch (MessagingException e) {
            logger.error("从 {} 发送至 {} HTML邮件失败：", from, to, e);
        }finally {
            return success;
        }
    }
    // 将该记录标记为已发送邮件状态
    private boolean sent(Long cmtId) {
        Long aLong = commentService.markCommentSent(cmtId);
        return aLong > 0;
    }

}
