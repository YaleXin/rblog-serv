package top.yalexin.rblog.entity;

import java.util.Date;
import java.util.List;


public class Comment {
    private Long id;
    private String nickname;
    private String email;
    private String content;
    private Date createTime;
    private boolean audited;
    private String OS;
    private String browser;
    private boolean sendEmailed;
    private boolean adminComment;
    private Long blogId;
    private List<Comment> replyComments;
    //父级评论
    private Long parentCommentId;
    // 所艾特的昵称
    private String replyNickname;
    private String blogName;

    public String getBlogName() {
        return blogName;
    }

    public void setBlogName(String blogName) {
        this.blogName = blogName;
    }

    public String getOS() {
        return OS;
    }

    public void setOS(String OS) {
        this.OS = OS;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getReplyNickname() {
        return replyNickname;
    }

    public void setReplyNickname(String replyNickname) {
        this.replyNickname = replyNickname;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public Long getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Long parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public boolean isSendEmailed() {
        return sendEmailed;
    }

    public void setSendEmailed(boolean sendEmailed) {
        this.sendEmailed = sendEmailed;
    }

    public boolean isAudited() {
        return audited;
    }

    public void setAudited(boolean audited) {
        this.audited = audited;
    }


    public boolean isAdminComment() {
        return adminComment;
    }

    public void setAdminComment(boolean adminComment) {
        this.adminComment = adminComment;
    }


    public List<Comment> getReplyComments() {
        return replyComments;
    }

    public void setReplyComments(List<Comment> replyComments) {
        this.replyComments = replyComments;
    }


    public Comment() {
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", audited=" + audited +
                ", OS='" + OS + '\'' +
                ", browser='" + browser + '\'' +
                ", sendEmailed=" + sendEmailed +
                ", adminComment=" + adminComment +
                ", blogId=" + blogId +
                ", replyComments=" + replyComments +
                ", parentCommentId=" + parentCommentId +
                ", replyNickname='" + replyNickname + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
