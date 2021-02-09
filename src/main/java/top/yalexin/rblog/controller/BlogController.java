/**
 * Author: Yalexin
 * Email: me@yalexin.top
 **/
package top.yalexin.rblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.yalexin.rblog.entity.Blog;
import top.yalexin.rblog.service.BlogService;
import top.yalexin.rblog.service.BlogServiceImpl;
import top.yalexin.rblog.util.PageRequest;
import top.yalexin.rblog.util.PageResult;

import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private BlogServiceImpl blogServiceImpl;

    @ResponseBody
    @GetMapping("/all")
    public List getAllBlogs(){
        System.out.println("getAllBlogs");
        return blogServiceImpl.getBlogList();
    }

    @ResponseBody
    @PostMapping(value="/blogPage")
    public Object findPage(@RequestBody PageRequest pageQuery) {
        System.out.println("findPage");

        System.out.println("pageQuery = " + pageQuery);
        PageResult blogByPage = blogService.getBlogByPage(pageQuery);
        return blogByPage;
    }
    @ResponseBody
    @GetMapping("/{id}")
    public Blog getOneBlog(@PathVariable("id") Long id){
        return blogService.getBlogById(id);
    }

    @ResponseBody
    @PutMapping("/put")
    public Blog  addOneBlog(@RequestBody Blog blog){
        return blogService.addBlog(blog);
    }

}
