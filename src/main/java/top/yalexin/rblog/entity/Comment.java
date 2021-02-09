package top.yalexin.rblog.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "t_comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickname;
    private String email;
    private String avatar;
    private String content;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    private boolean audited;

    private boolean sendEmailed;

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", audited=" + audited +
                ", sendEmailed=" + sendEmailed +
                ", adminComment=" + adminComment +
                ", blog=" + blog +
                ", replyComments=" + replyComments +
                ", parentComment=" + parentComment +
                '}';
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

    private boolean adminComment;

    public boolean isAdminComment() {
        return adminComment;
    }

    public void setAdminComment(boolean adminComment) {
        this.adminComment = adminComment;
    }

    @ManyToOne
    private Blog blog;
    @OneToMany(mappedBy = "parentComment")
    private List<Comment> replyComments = new ArrayList<>();
    @ManyToOne
    private Comment parentComment;
    public Blog getBlog() {
        return blog;
    }

    public List<Comment> getReplyComments() {
        return replyComments;
    }

    public void setReplyComments(List<Comment> replyComments) {
        this.replyComments = replyComments;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public Comment() {
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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
