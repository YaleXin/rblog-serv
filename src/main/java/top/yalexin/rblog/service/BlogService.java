/**
 * Author: Yalexin
 * Email: 181303209@yzu.edu.cn
 **/
package top.yalexin.rblog.service;

import top.yalexin.rblog.entity.Blog;
import top.yalexin.rblog.util.PageResult;


import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

public interface BlogService {
    List<Blog> getBlogList();
    List<Blog> getTopBlogList();

    PageResult getBlogByPage(Long pageNum, Long pageSize);

    PageResult getBlogByIntervalAndNameOrContent(Long pageNum, Long pageSize, String nameOrContent);

    PageResult getBlogByPageAndCategoryId(Long pageNum, Long pageSize, Long categoryId);

    PageResult getBlogByPageAndTagId(Long pageNum, Long pageSize, Long tagId);

    Blog getBlogById(Long id);

    // 返回解析 markdown 语法后的博客文章
    Blog getParsedBlogById(Long id, HttpServletRequest request);

    Blog addBlog(Blog blog);

    Blog updateBlog(Blog blog);

    Long deleteBlogById(Long id);


}
