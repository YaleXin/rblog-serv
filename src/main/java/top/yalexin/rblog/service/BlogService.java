/**
 * Author: Yalexin
 * Email: 181303209@yzu.edu.cn
 **/
package top.yalexin.rblog.service;

import top.yalexin.rblog.entity.Blog;
import top.yalexin.rblog.util.PageResult;
//import top.yalexin.rblog.util.PageRequest;
//import top.yalexin.rblog.util.PageResult;

import java.util.List;
import java.util.Map;

public interface BlogService {
    List<Blog> getBlogList();

    PageResult getBlogByPage(Long pageNum, Long pageSize);

    PageResult getBlogByIntervalAndNameOrContent(Long pageNum, Long pageSize, String nameOrContent);

    PageResult getBlogByPageAndCategoryId(Long pageNum, Long pageSize, Long categoryId);

    PageResult getBlogByPageAndTagId(Long pageNum, Long pageSize, Long tagId);

    Blog getBlogById(Long id);

    // 返回解析 markdown 语法后的博客文章
    Blog getParsedBlogById(Long id);

    Blog addBlog(Blog blog);

    Blog updateBlog(Blog blog);

    Long deleteBlogById(Long id);


}
