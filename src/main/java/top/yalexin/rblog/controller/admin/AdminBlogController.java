/**
 * Author: Yalexin
 * Email: me@yalexin.top
 **/
package top.yalexin.rblog.controller.admin;


import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.yalexin.rblog.entity.Blog;
import top.yalexin.rblog.service.BlogService;

import java.util.HashMap;

@Controller
@ResponseBody
@RequestMapping("/admin/blog")
public class AdminBlogController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BlogService blogService;


    @PutMapping("/add")
    ResponseEntity addBlog(@RequestBody HashMap json) {
        Blog blog = (Blog) JSON.parseObject(json.get("data").toString(), Blog.class);
        logger.debug("前端blog-----> {}", blog);
        HashMap<String, Object> map = new HashMap<>();
        Blog addBlog = blogService.addBlog(blog);
        logger.debug("after saveBlog-----> {}", addBlog);
        if (addBlog == null) return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        map.put("addBlog", addBlog);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity deleteBlog(@PathVariable("id") Long id) {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("result", blogService.deleteBlogById(id));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PutMapping("/modify")
    ResponseEntity modifyBlog(@RequestBody HashMap json) {
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

}
