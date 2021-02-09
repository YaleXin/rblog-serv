/**
 * Author: Yalexin
 * Email: me@yalexin.top
 **/
package top.yalexin.rblog.controller;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.yalexin.rblog.entity.Blog;
import top.yalexin.rblog.service.BlogService;
import top.yalexin.rblog.service.BlogServiceImpl;
import top.yalexin.rblog.util.PageRequest;
import top.yalexin.rblog.util.PageResult;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BlogService blogService;

    @Autowired
    private BlogServiceImpl blogServiceImpl;

    @ResponseBody
    @GetMapping("/all")
    public List getAllBlogs() {
        System.out.println("getAllBlogs");
        return blogServiceImpl.getBlogList();
    }

    @ResponseBody
    @PostMapping(value = "/blogPage")
    public ResponseEntity findPage(@RequestParam(value = "pageNum", required = false, defaultValue = "1") String pageNum,
                                   @RequestParam(value = "pageSize", required = false, defaultValue = "5") String pageSize) {

//        PageRequest pageQuery = JSON.parseObject(json.get("data").toString(), PageRequest.class);
//        logger.info("pageQuery = {}" + pageQuery);
//        PageResult blogByPage = blogService.getBlogByPage(pageQuery);
        Integer pageNumI = Integer.parseInt(pageNum);
        Integer pageSizeI = Integer.parseInt(pageSize);
        PageResult blogByPage = blogService.getBlogByPage(1, 5);
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", blogByPage);
        return new ResponseEntity<>(map, HttpStatus.OK);

    }

    @ResponseBody
    @GetMapping("/{id}")
    public Blog getOneBlog(@PathVariable("id") Long id) {
        return blogService.getBlogById(id);
    }

    @ResponseBody
    @PutMapping("/put")
    public Blog addOneBlog(@RequestBody Blog blog) {
        return blogService.addBlog(blog);
    }

}
