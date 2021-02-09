/**
 * Author: Yalexin
 * Email: me@yalexin.top
 **/
package top.yalexin.rblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.yalexin.rblog.entity.Category;
import top.yalexin.rblog.mapper.CategoryMapper;

import java.util.List;
@Transactional
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> getCategoryList() {
        return categoryMapper.findAllCategories();
    }

    @Override
    public Category addCategory(Category category) {
        if (category == null) return null;
        Long newId = categoryMapper.insertCategory(category);
        if(newId == null)return null;
        else category.setId(newId);
        return category;
    }
}
