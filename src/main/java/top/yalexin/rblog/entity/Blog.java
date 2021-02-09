package top.yalexin.rblog.entity;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "t_blog") // 指定数据库表名
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "标题不能为空")
    private String name;
    @NotBlank(message = "内容不能为空")
    @Basic(fetch = FetchType.LAZY)
    @Lob
    private String content;
    @Lob
    private String description;
    private Integer views;
    private boolean published;

    @Transient // 不需要保存到数据库 但是前端页面需要
    private String tagIds;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    //    @NotBlank(message = "分类不能为空")
    @ManyToOne
    private Category category;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }



    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>();


    //    文章中有新标签时，数据库中的标签表也会更新
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> tags = new ArrayList<>();

    public List<Comment> getComments() {
        return comments;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", description='" + description + '\'' +
                ", views=" + views +
                ", published=" + published +
                ", tagIds='" + tagIds + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", category=" + category +
                ", comments=" + comments +
                ", tags=" + tags +
                '}';
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Blog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void init() {
        this.tagIds = tags2Ids(this.getTags());
    }

    // 将List<Tag> 转为对应的id 即： 1, 2, 3, 4,   用于前端value
    private String tags2Ids(List<Tag> tags) {
        int count = 1;
        if (!tags.isEmpty()) {
            StringBuffer stringBuffer = new StringBuffer();
            for (Tag t :
                    tags) {
                stringBuffer.append(t.getId());
                if (count < tags.size()) {
                    stringBuffer.append(",");
                    count++;
                }
            }
            return stringBuffer.toString();
        } else {
            return tagIds;
        }
    }

}
