/**
 * Author: Yalexin
 * Email: me@yalexin.top
 **/
package top.yalexin.rblog.controller.admin;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.yalexin.rblog.entity.Blog;
import top.yalexin.rblog.service.BlogService;
import top.yalexin.rblog.service.BlogServiceImpl;

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
}
