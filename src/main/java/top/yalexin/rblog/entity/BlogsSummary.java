package top.yalexin.rblog.entity;

public class BlogsSummary {
    // 文章总数
    private Long totalBlogs;
    // 文章总字数
    private Long totalBlogWords;
    // 总阅览数
    private Long totalRead;
    // 最早发文时间
    private Long earliest;

    // 最晚发文时间
    private Long latest;

    // 单篇文章最高阅览数
    private Long maxRead;

    public BlogsSummary() {
    }

    public Long getTotalBlogs() {
        return totalBlogs;
    }

    public void setTotalBlogs(Long totalBlogs) {
        this.totalBlogs = totalBlogs;
    }

    public Long getTotalBlogWords() {
        return totalBlogWords;
    }

    public void setTotalBlogWords(Long totalBlogWords) {
        this.totalBlogWords = totalBlogWords;
    }

    public Long getTotalRead() {
        return totalRead;
    }

    public void setTotalRead(Long totalRead) {
        this.totalRead = totalRead;
    }

    public Long getEarliest() {
        return earliest;
    }

    public void setEarliest(Long earliest) {
        this.earliest = earliest;
    }

    public Long getLatest() {
        return latest;
    }

    public void setLatest(Long latest) {
        this.latest = latest;
    }

    public Long getMaxRead() {
        return maxRead;
    }

    public void setMaxRead(Long maxRead) {
        this.maxRead = maxRead;
    }
}
