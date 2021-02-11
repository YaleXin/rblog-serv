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
import top.yalexin.rblog.entity.Category;
import top.yalexin.rblog.service.CategoryService;
import top.yalexin.rblog.util.PageResult;

import java.util.HashMap;

@Controller
@ResponseBody
@RequestMapping("/admin/category")
public class AdminCategoryController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categoryPage")
    ResponseEntity getCategoryPage(@RequestParam(value = "pageNum", required = false, defaultValue = "1") String pageNum,
                                   @RequestParam(value = "pageSize", required = false, defaultValue = "1") String pageSize) {
        long pageNumL = Long.parseLong(pageNum);
        long pageSizeL = Long.parseLong(pageSize);
        PageResult categoryByPage = categoryService.getCategoryByPage(pageNumL, pageSizeL);
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", categoryByPage);
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @PutMapping("/modify")
    ResponseEntity modifyCategory(@RequestBody HashMap json) {
        Category category = JSON.parseObject(json.get("data").toString(), Category.class);
        logger.debug("前端category-----> {}", category);
        HashMap<String, Object> map = new HashMap<>();
        Category newCategory = categoryService.updateCategory(category);
        logger.debug("after editCategory-----> {}", newCategory);
        if (newCategory == null) return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        map.put("newCategory", newCategory);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity deleteCategory(@PathVariable("id") Long id) {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("result", categoryService.deleteCategoryById(id));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }


}
