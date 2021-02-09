/**
 * Author: Yalexin
 * Email: me@yalexin.top
 **/
package top.yalexin.rblog.service;

import top.yalexin.rblog.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getCategoryList();

    Category addCategory(Category category);
}
