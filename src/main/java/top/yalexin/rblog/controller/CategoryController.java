/**
 * Author: Yalexin
 * Email: me@yalexin.top
 **/
package top.yalexin.rblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.yalexin.rblog.entity.Category;
import top.yalexin.rblog.service.CategoryService;

import java.util.HashMap;
import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/all")
    HashMap getAllCategories() {
        HashMap<String, Object> hashMap = new HashMap<>();
        List<Category> categories = categoryService.getCategoryList();
        hashMap.put("categories", categories);
        return hashMap;
    }
}
