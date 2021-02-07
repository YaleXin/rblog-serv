//package top.yalexin.rblog.entity;
//
//import org.hibernate.validator.constraints.NotBlank;
//
//import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//
//@Entity
//@Table(name = "t_blog") // 指定数据库表名
//public class Blog {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @NotBlank(message = "标题不能为空")
//    private String title;
//    @NotBlank(message = "内容不能为空")
//    @Basic(fetch = FetchType.LAZY)
//    @Lob
//    private String content;
//    @NotBlank(message = "首图不能为空")
//    private String firstPicture;
//    private String flag;
//    @Lob
//    private String description;
//    private Integer views;
//    private boolean appreciation;
//    private boolean shareStatement;
//    private boolean commentabled;
//    private boolean published;
//    private boolean recommend;
//
//    @Transient // 不需要保存到数据库 但是前端页面需要
//    private String tagIds;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date createTime;
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date updateTime;
//    //    @NotBlank(message = "分类不能为空")
//    @ManyToOne
//    private Type type;
//
//    @Override
//    public String toString() {
//        return "Blog{" +
//                "id=" + id +
//                ", title='" + title + '\'' +
//                ", content='" + content + '\'' +
//                ", firstPicture='" + firstPicture + '\'' +
//                ", flag='" + flag + '\'' +
//                ", description='" + description + '\'' +
//                ", views=" + views +
//                ", appreciation=" + appreciation +
//                ", shareStatement=" + shareStatement +
//                ", commentabled=" + commentabled +
//                ", published=" + published +
//                ", recommend=" + recommend +
//                ", tagIds='" + tagIds + '\'' +
//                ", createTime=" + createTime +
//                ", updateTime=" + updateTime +
//                ", type=" + type +
//                ", comments=" + comments +
//                ", user=" + user +
//                ", tags=" + tags +
//                '}';
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//
//    public String getTagIds() {
//        return tagIds;
//    }
//
//    public void setTagIds(String tagIds) {
//        this.tagIds = tagIds;
//    }
//
//
//    public User getUser() {
//        return user;
//    }
//
//    @OneToMany(mappedBy = "blog")
//    private List<Comment> comments = new ArrayList<>();
//
//    @ManyToOne
//    private User user;
//
//    //    文章中有新标签时，数据库中的标签表也会更新
//    @ManyToMany(cascade = {CascadeType.PERSIST})
//    private List<Tag> tags = new ArrayList<>();
//
//    public List<Comment> getComments() {
//        return comments;
//    }
//
//    public void setComments(List<Comment> comments) {
//        this.comments = comments;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public List<Tag> getTags() {
//        return tags;
//    }
//
//    public void setTags(List<Tag> tags) {
//        this.tags = tags;
//    }
//
//    public Type getType() {
//        return type;
//    }
//
//    public void setType(Type type) {
//        this.type = type;
//    }
//
//    public Blog() {
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//
//    public String getFirstPicture() {
//        return firstPicture;
//    }
//
//    public void setFirstPicture(String firstPicture) {
//        this.firstPicture = firstPicture;
//    }
//
//    public String getFlag() {
//        return flag;
//    }
//
//    public void setFlag(String flag) {
//        this.flag = flag;
//    }
//
//    public Integer getViews() {
//        return views;
//    }
//
//    public void setViews(Integer views) {
//        this.views = views;
//    }
//
//    public boolean isAppreciation() {
//        return appreciation;
//    }
//
//    public void setAppreciation(boolean appreciation) {
//        this.appreciation = appreciation;
//    }
//
//    public boolean isShareStatement() {
//        return shareStatement;
//    }
//
//    public void setShareStatement(boolean shareStatement) {
//        this.shareStatement = shareStatement;
//    }
//
//    public boolean isCommentabled() {
//        return commentabled;
//    }
//
//    public void setCommentabled(boolean commentabled) {
//        this.commentabled = commentabled;
//    }
//
//    public boolean isPublished() {
//        return published;
//    }
//
//    public void setPublished(boolean published) {
//        this.published = published;
//    }
//
//    public boolean isRecommend() {
//        return recommend;
//    }
//
//    public void setRecommend(boolean recommend) {
//        this.recommend = recommend;
//    }
//
//    public Date getCreateTime() {
//        return createTime;
//    }
//
//    public void setCreateTime(Date createTime) {
//        this.createTime = createTime;
//    }
//
//    public Date getUpdateTime() {
//        return updateTime;
//    }
//
//    public void setUpdateTime(Date updateTime) {
//        this.updateTime = updateTime;
//    }
//
//    public void init() {
//        this.tagIds = tags2Ids(this.getTags());
//    }
//
//    // 将List<Tag> 转为对应的id 即： 1, 2, 3, 4,   用于前端value
//    private String tags2Ids(List<Tag> tags) {
//        int count = 1;
//        if (!tags.isEmpty()) {
//            StringBuffer stringBuffer = new StringBuffer();
//            for (Tag t :
//                    tags) {
//                stringBuffer.append(t.getId());
//                if (count < tags.size()) {
//                    stringBuffer.append(",");
//                    count++;
//                }
//            }
//            return stringBuffer.toString();
//        } else {
//            return tagIds;
//        }
//    }
//
//}
