package top.yalexin.rblog.entity;

import java.util.List;

public class Link {

    private Long id;

    private String type;

    private List<LinkItem> content;

    public Link() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<LinkItem> getContent() {
        return content;
    }

    public void setContent(List<LinkItem> content) {
        this.content = content;
    }
}
