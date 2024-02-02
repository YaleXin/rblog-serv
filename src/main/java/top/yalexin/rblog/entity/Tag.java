package top.yalexin.rblog.entity;

import java.util.ArrayList;
import java.util.List;


public class Tag {
    private Long id;
    private String name;
    private Long blogsCnt;

    private List<Blog> blogs = new ArrayList<>();

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    public Tag() {
    }
    public Long getBlogsCnt() {
        return blogsCnt;
    }

    public void setBlogsCnt(Long blogsCnt) {
        this.blogsCnt = blogsCnt;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", blogsCnt=" + blogsCnt +
                ", blogs=" + blogs +
                '}';
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
}
