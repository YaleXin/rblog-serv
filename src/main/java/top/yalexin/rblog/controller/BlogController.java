/**
 * Author: Yalexin
 * Email: me@yalexin.top
 **/
package top.yalexin.rblog.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.yalexin.rblog.constant.CacheNameConstant;
import top.yalexin.rblog.entity.Blog;
import top.yalexin.rblog.service.BlogService;
import top.yalexin.rblog.util.PageResult;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@ResponseBody
@RestController
@RequestMapping("/blog")
public class BlogController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BlogService blogService;


    @Cacheable(value = CacheNameConstant.BLOG_CACHE, key = "'topBlogs'")
    @GetMapping("/top")
    public ResponseEntity findTopBlogs(){
        List<Blog> topBlogList = blogService.getTopBlogList();
        HashMap<String, Object> map = new HashMap<>();
        map.put("topBlogs", topBlogList);
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @GetMapping("/all")
    public List getAllBlogs() {
        System.out.println("getAllBlogs");
        return blogService.getBlogList();
    }

    @GetMapping(value = "/blogPage")
    public ResponseEntity findPage(@RequestParam(value = "pageNum", required = false, defaultValue = "1") String pageNum,
                                   @RequestParam(value = "pageSize", required = false, defaultValue = "1") String pageSize) {
        long pageNumL = Long.parseLong(pageNum);
        long pageSizeL = Long.parseLong(pageSize);
        PageResult blogByPage = blogService.getBlogByPage(pageNumL, pageSizeL);
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", blogByPage);
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Cacheable(value = CacheNameConstant.BLOG_CACHE, key = "#id")
    @GetMapping("/{id}")
    public ResponseEntity getOneBlog(HttpServletRequest request, @PathVariable("id") Long id) {
        HashMap<String, Object> map = new HashMap<>();
        Blog parsedBlogById = blogService.getParsedBlogById(id, request);
        map.put("blog", parsedBlogById);
        return new ResponseEntity(map, HttpStatus.OK);
    }


    @GetMapping("/search")
    public ResponseEntity getBlogListBySearch(@RequestParam(value = "nameOrContent", required = false, defaultValue = "") String nameOrContent,
                                              @RequestParam(value = "pageNum", required = false, defaultValue = "1") String pageNum,
                                              @RequestParam(value = "pageSize", required = false, defaultValue = "1") String pageSize) {
        long pageNumL = Long.parseLong(pageNum);
        long pageSizeL = Long.parseLong(pageSize);

        logger.info("nameOrContent ===> {}", nameOrContent);

        HashMap<String, Object> map = new HashMap<>();
        PageResult page = blogService.getBlogByIntervalAndNameOrContent(pageNumL, pageSizeL, nameOrContent);
        map.put("page", page);
        return new ResponseEntity(map, HttpStatus.OK);
    }

}
