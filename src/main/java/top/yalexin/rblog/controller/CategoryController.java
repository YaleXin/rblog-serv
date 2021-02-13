/**
 * Author: Yalexin
 * Email: me@yalexin.top
 **/
package top.yalexin.rblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.yalexin.rblog.entity.Category;
import top.yalexin.rblog.service.BlogService;
import top.yalexin.rblog.service.CategoryService;
import top.yalexin.rblog.util.PageResult;

import java.util.HashMap;
import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BlogService blogService;

    @GetMapping("/all")
    ResponseEntity getPageBlogByCategory() {
        List<Category> categories = categoryService.getCategoryList();
        HashMap<String, Object> map = new HashMap<>();
        map.put("categories", categories);
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    ResponseEntity getPageBlogByCategory(@PathVariable("categoryId") Long categoryId,
                                         @RequestParam(value = "pageNum", required = false, defaultValue = "1") String pageNum,
                                         @RequestParam(value = "pageSize", required = false, defaultValue = "1") String pageSize) {
        long pageNumL = Long.parseLong(pageNum);
        long pageSizeL = Long.parseLong(pageSize);

        PageResult pageResult = blogService.getBlogByPageAndCategoryId(pageNumL, pageSizeL, categoryId);
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", pageResult);
        return new ResponseEntity(map, HttpStatus.OK);
    }


}
