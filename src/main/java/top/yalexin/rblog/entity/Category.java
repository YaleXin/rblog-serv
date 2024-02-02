package top.yalexin.rblog.entity;

import java.util.ArrayList;
import java.util.List;


public class Category {
    private Long id;
    private String name;

    private List<Blog> blogs = new ArrayList<>();
    private Long blogsCnt;

    public Category() {
    }

    public Long getBlogsCnt() {
        return blogsCnt;
    }

    public void setBlogsCnt(Long blogsCnt) {
        this.blogsCnt = blogsCnt;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", blogs=" + blogs +
                ", blogsCnt=" + blogsCnt +
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
