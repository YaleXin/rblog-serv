/**
 * Author: Yalexin
 * Email: me@yalexin.top
 **/
package top.yalexin.rblog.util;

import java.util.List;

public class PageResult {
    Long pageNum;
    Long pageSize;
    Long totalPage;
    Long totalSize;
    List<?> content;

    public List<?> getContent() {
        return content;
    }

    void setContent(List<?> content) {
        this.content = content;
    }

    void setPageNum(Long pageNum) {
        this.pageNum = pageNum;
    }

    void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    void setTotalPage(Long totalPage) {
        this.totalPage = totalPage;
    }

    void setTotalSize(Long totalSize) {
        this.totalSize = totalSize;
    }

    public Long getPageNum() {
        return pageNum;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public Long getTotalPage() {
        return totalPage;
    }

    public Long getTotalSize() {
        return totalSize;
    }
}
