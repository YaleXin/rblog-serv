/**
 * Author: Yalexin
 * Email: me@yalexin.top
 **/
package top.yalexin.rblog.controller.admin;


import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.yalexin.rblog.constant.CacheNameConstant;
import top.yalexin.rblog.entity.Blog;
import top.yalexin.rblog.service.BlogService;
import top.yalexin.rblog.util.PageResult;

import java.util.HashMap;

@Controller
@ResponseBody
@RequestMapping("/admin/blog")
public class AdminBlogController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BlogService blogService;


    // 清楚归档页面的缓存
    @CacheEvict(value = CacheNameConstant.ARCHIVE_CACHE, allEntries = true)
    @PutMapping("/add")
    public ResponseEntity addBlog(@RequestBody HashMap json) {
        Blog blog = (Blog) JSON.parseObject(json.get("data").toString(), Blog.class);
        logger.debug("前端blog-----> {}", blog);
        HashMap<String, Object> map = new HashMap<>();
        Blog addBlog = blogService.addBlog(blog);
        logger.debug("after saveBlog-----> {}", addBlog);
        if (addBlog == null) return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        map.put("addBlog", addBlog);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @CacheEvict(value = CacheNameConstant.BLOG_CACHE, key = "#id")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteBlog(@PathVariable("id") Long id) {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("result", blogService.deleteBlogById(id));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
// 有点问题，暂时不清除缓存   等缓存自动失效
//    @CacheEvict(value = CacheNameConstant.BLOG_CACHE, key = "#json['data']['id']")
    @PutMapping("/modify")
    public ResponseEntity modifyBlog(@RequestBody HashMap json) {
        Blog blog = (Blog) JSON.parseObject(json.get("data").toString(), Blog.class);
        logger.debug("前端blog-----> {}", blog);
        HashMap<String, Object> map = new HashMap<>();
        Blog newBlog = blogService.updateBlog(blog);
        logger.debug("after editBlog-----> {}", newBlog);
        if (newBlog == null) return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        map.put("newBlog", newBlog);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Blog getOneBlog(@PathVariable("id") Long id) {
        return blogService.getBlogById(id);
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


}
