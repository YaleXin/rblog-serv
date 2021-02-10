/**
 * Author: Yalexin
 * Email: me@yalexin.top
 **/
package top.yalexin.rblog.util;

import java.util.List;

public class PageUtils {
    public static PageResult setResult(List<?> contet, Long pageNum, Long pageSize, Long totalPage, Long totalSize) {
        PageResult pageResult = new PageResult();
        pageResult.setContent(contet);
        pageResult.setPageNum(pageNum);
        pageResult.setPageSize(pageSize);
        pageResult.setTotalPage(totalPage);
        pageResult.setTotalSize(totalSize);
        return pageResult;
    }
}
