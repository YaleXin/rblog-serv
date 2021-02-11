/**
 * Author: Yalexin
 * Email: me@yalexin.top
 **/
package top.yalexin.rblog.service;

import top.yalexin.rblog.entity.Category;
import top.yalexin.rblog.util.PageResult;

import java.util.List;

public interface CategoryService {
    List<Category> getCategoryList();

    Category addCategory(Category category);

    PageResult getCategoryByPage(Long pageNum, Long pageSize);

    Category updateCategory(Category category);

    Long deleteCategoryById(Long id);

}
