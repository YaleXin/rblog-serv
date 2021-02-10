/**
 * Author: Yalexin
 * Email: 181303209@yzu.edu.cn
 **/
package top.yalexin.rblog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.yalexin.rblog.entity.Blog;
import top.yalexin.rblog.entity.Category;
import top.yalexin.rblog.entity.Tag;
import top.yalexin.rblog.exception.DataFormatErrorException;
import top.yalexin.rblog.mapper.BlogMapper;
import top.yalexin.rblog.mapper.CategoryMapper;
import top.yalexin.rblog.mapper.TagMapper;
import top.yalexin.rblog.util.PageUtils;
import top.yalexin.rblog.util.PageResult;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<Blog> getBlogList() {
        System.out.println("BlogServiceImpl.getBlogList()");
        return blogMapper.findAllBlog();
    }

    /**
     * @param requestPageNum  从1开始
     * @param requestPageSize
     * @return
     */
    @Override
    public PageResult getBlogByPage(Long requestPageNum, Long requestPageSize) {
        Long totalSize = blogMapper.countBlog();
        // 前端传来的 size 若为非正数，则默认 size=1
        long pageSize = Math.max(requestPageSize, 1);
        // 前端传来的页面编号若非数， 则默认 请求第一页
        long pageNum = requestPageNum > 0 ? (requestPageNum - 1) : 0;

        long totalPage = totalSize / pageSize;

        List<Blog> blogs = blogMapper.findBlogByInterval(pageNum * pageSize, pageSize);
        // 加一 是方便前端
        PageResult pageResult = PageUtils.setResult(blogs, pageNum + 1, pageSize, totalPage, totalSize);
        return pageResult;
    }


    @Override
    public Blog getBlogById(Long id) {
        if (id == null || id < 0) return null;
        else return blogMapper.findBlog(id);
    }

    /**
     * 验证待插入的博客的正确性并插入数据库
     *
     * @param blog
     * @return
     */
    @Override
    public Blog addBlog(Blog blog) {
        try {
            if (!checkBlog(blog)) {
                return null;
            }
            Date now = new Date();
            if (blog.getCreateTime().getTime() > now.getTime()) {
                blog.setCreateTime(now);
            }
            blog.setUpdateTime(now);
            Category category = blog.getCategory();
            //若文章的分类是新的分类
            if (category.getId() < 0) {
                Long aLong = categoryMapper.insertCategory(category);
                if (aLong == null) return null;
                else category.setId(aLong);
            }
            List<Tag> tags = blog.getTags();
            // 如果标签中有新的 则插入
            for (Tag tag : tags) {
                if (tag.getId() < 0) {
                    Long aLong = tagMapper.insertTag(tag);
                    // 插入异常
                    if (aLong == null) return null;
                    else tag.setId(aLong);
                }
            }
            Long aLong = blogMapper.insertBlog(blog);
            if (aLong < 1) {
                return null;
            }
            for (Tag tag : tags) {
                // 写入第三张表
                blogMapper.insertBlogTag(blog.getId(), tag.getId());
            }
            return blog;
        } catch (Exception e) {
            logger.error("error --> {}", e.getMessage());
            throw new DataFormatErrorException();
        }
    }

    private boolean checkBlog(Blog blog) {
        if (blog == null) return false;
        else if (blog.getName() == null || "".equals(blog.getName().trim())) return false;
        else return blog.getCategory().getId() != null;
    }

    private boolean checkTagAndCategory(Blog blog) {
        Category category = blog.getCategory();
        if (category == null || (category.getId() == -1 && (category.getName() == null || category.getName().trim().equals("")))) {
            return false;
        }
        List<Tag> tags = blog.getTags();
        for (Tag tag : tags) {
            if (tag.getId() == -1 && (tag.getId() == -1 && (tag.getName() == null || tag.getName().trim().equals("")))) {
                return false;
            }
        }
        return true;
    }


}
