/**
 * Author: Yalexin
 * Email: 181303209@yzu.edu.cn
 **/
package top.yalexin.rblog.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.yalexin.rblog.entity.Blog;
import top.yalexin.rblog.entity.Category;
import top.yalexin.rblog.entity.Tag;
import top.yalexin.rblog.exception.DataFormatErrorException;
import top.yalexin.rblog.mapper.BlogMapper;
import top.yalexin.rblog.mapper.CategoryMapper;
import top.yalexin.rblog.mapper.TagMapper;
import top.yalexin.rblog.service.BlogService;
import top.yalexin.rblog.util.MarkdownUtils;
import top.yalexin.rblog.util.PageUtils;
import top.yalexin.rblog.util.PageResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

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

    // 防抖时间，单位为毫秒（下面为1分钟）
    private final int ANTI_SHAKE_TIME = 1 * 60 * 1000;

    @Override
    public List<Blog> getBlogList() {
        System.out.println("BlogServiceImpl.getBlogList()");
        return blogMapper.findAllBlog();
    }

    @Override
    public List<Blog> getTopBlogList() {
        return blogMapper.findTopBlog();
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
    public PageResult getBlogByIntervalAndNameOrContent(Long requestPageNum, Long requestPageSize, String nameOrContent) {
        Long totalSize = blogMapper.countBlogLikeNameOrContent("%" + nameOrContent + "%");
        // 前端传来的 size 若为非正数，则默认 size=1
        long pageSize = Math.max(requestPageSize, 1);
        // 前端传来的页面编号若非数， 则默认 请求第一页
        long pageNum = requestPageNum > 0 ? (requestPageNum - 1) : 0;

        long totalPage = totalSize / pageSize;

        List<Blog> blogs = blogMapper.findBlogByIntervalAndNameOrContent(pageNum * pageSize, pageSize, "%" + nameOrContent + "%");
        // 加一 是方便前端
        PageResult pageResult = PageUtils.setResult(blogs, pageNum + 1, pageSize, totalPage, totalSize);
        return pageResult;
    }

    @Override
    public PageResult getBlogByPageAndCategoryId(Long requestPageNum, Long requestPageSize, Long categoryId) {
        Long totalSize = blogMapper.countBlogByCategory(categoryId);
        // 前端传来的 size 若为非正数，则默认 size=1
        long pageSize = Math.max(requestPageSize, 1);
        // 前端传来的页面编号若非数， 则默认 请求第一页
        long pageNum = requestPageNum > 0 ? (requestPageNum - 1) : 0;

        long totalPage = totalSize / pageSize;

        List<Blog> blogs = blogMapper.findBlogWithCategoryByInterval(pageNum * pageSize, pageSize, categoryId);

        PageResult pageResult = PageUtils.setResult(blogs, pageNum + 1, pageSize, totalPage, totalSize);
        return pageResult;
    }

    @Override
    public PageResult getBlogByPageAndTagId(Long requestPageNum, Long requestPageSize, Long tagId) {
        Long totalSize = blogMapper.countBlogByTag(tagId);
        // 前端传来的 size 若为非正数，则默认 size=1
        long pageSize = Math.max(requestPageSize, 1);
        // 前端传来的页面编号若非数， 则默认 请求第一页
        long pageNum = requestPageNum > 0 ? (requestPageNum - 1) : 0;

        long totalPage = totalSize / pageSize;

        List<Blog> blogs = blogMapper.findBlogWithTagByInterval(pageNum * pageSize, pageSize, tagId);

        PageResult pageResult = PageUtils.setResult(blogs, pageNum + 1, pageSize, totalPage, totalSize);
        return pageResult;
    }


    @Override
    public Blog getBlogById(Long id) {
        if (id == null || id < 0) return null;
        else return blogMapper.findBlog(id);
    }

    @Override
    public Blog getParsedBlogById(Long id, HttpServletRequest request) {
        if (id == null || id < 0) return null;
        Blog rawBlog = blogMapper.findBlog(id);
        if (rawBlog != null) {
            String markdownContent = MarkdownUtils.markdownToHtmlExtensions(rawBlog.getContent());
            rawBlog.setContent(markdownContent);
            rawBlog.setViews(rawBlog.getViews() + tryUpdateView(request, rawBlog.getId()));

        }
        return rawBlog;
    }

    /**
     * 检查是否需要将访问量加 1
     * @param request
     * @param blogId
     * @return
     */
    int tryUpdateView(HttpServletRequest request, Long blogId) {
        Date now = new Date();

        HttpSession session = request.getSession();
        String key = session.getId() + "#tryUpdateView@" + blogId;
        Date time = (Date) session.getAttribute(key);
        // 该阶段时间内第一次访问
        if (time == null) {
            blogMapper.updateBlogViews(blogId);
            session.setAttribute(key, now);
            return 1;
        } else {
            // 如果是在时间窗口内，啥都不做，否则，更新一次数据
            if (now.getTime() - time.getTime() > ANTI_SHAKE_TIME) {
                blogMapper.updateBlogViews(blogId);
                session.setAttribute(key, now);
                return 1;
            }
            return 0;
        }

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
                throw new DataFormatErrorException();
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
                if (aLong < 0) throw new DataFormatErrorException();
            }
            List<Tag> tags = blog.getTags();
            // 如果标签中有新的 则插入
            for (Tag tag : tags) {
                if (tag.getId() < 0) {
                    Long aLong = tagMapper.insertTag(tag);
                    // 插入异常
                    if (aLong < 0) throw new DataFormatErrorException();
                }
            }
            Long aLong = blogMapper.insertBlog(blog);
            if (aLong < 1) {
                throw new DataFormatErrorException();
            }
            for (Tag tag : tags) {
                // 写入第三张表
                Long aLong1 = blogMapper.insertBlogTag(blog.getId(), tag.getId());
                if (aLong1 < 0) throw new DataFormatErrorException();
            }
            return blog;
        } catch (Exception e) {
            logger.error("error --> {}", e.getMessage());
            throw new DataFormatErrorException();
        }
    }

    @Override
    public Blog updateBlog(Blog blog) {
        try {
            if (!checkBlog(blog)) {
                throw new DataFormatErrorException();
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
                if (aLong < 0) throw new DataFormatErrorException();
            }
            List<Tag> tags = blog.getTags();
            // 如果标签中有新的 则插入
            for (Tag tag : tags) {
                if (tag.getId() < 0) {
                    Long aLong = tagMapper.insertTag(tag);
                    // 插入异常
                    if (aLong < 0) new DataFormatErrorException();
                }
            }
            Long aLong = blogMapper.updateBlog(blog);

            // 更新文章时 先全删除该文章的标签，再设置新的标签
            blogMapper.emptyBlogTags(blog.getId());
            for (Tag tag : tags) {
                // 设置新的标签
                Long insertResult = blogMapper.insertBlogTag(blog.getId(), tag.getId());
                if (insertResult < 0) throw new DataFormatErrorException();
            }
            return blog;
        } catch (Exception e) {
            logger.error("error --> {}", e.getMessage());
            throw new DataFormatErrorException();
        }
    }

    @Override
    public Long deleteBlogById(Long id) {
        if (id == null || id < 0) return null;
        return blogMapper.deleteBlog(id);
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
