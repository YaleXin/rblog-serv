/**
 * Author: Yalexin
 * Email: me@yalexin.top
 **/
package top.yalexin.rblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.yalexin.rblog.entity.Blog;
import top.yalexin.rblog.entity.Category;
import top.yalexin.rblog.mapper.CategoryMapper;
import top.yalexin.rblog.util.PageResult;
import top.yalexin.rblog.util.PageUtils;

import java.util.List;

@Transactional
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;


    @Override
    public List<Category> getCategoryListWithBlogCnt() {
        return categoryMapper.findAllCategoriesWithBlogCnt();
    }

    @Override
    public Category addCategory(Category category) {
        if (category == null) return null;
        Long newId = categoryMapper.insertCategory(category);
        if (newId == null) return null;
        else category.setId(newId);
        return category;
    }

    @Override
    public PageResult getCategoryByPage(Long requestPageNum, Long requestPageSize) {
        Long totalSize = categoryMapper.countCategory();
        // 前端传来的 size 若为非正数，则默认 size=1
        long pageSize = Math.max(requestPageSize, 1);
        // 前端传来的页面编号若非数， 则默认 请求第一页
        long pageNum = requestPageNum > 0 ? (requestPageNum - 1) : 0;

        long totalPage = totalSize / pageSize;

        List<Category> categories = categoryMapper.findCategoryByInterval(pageNum * pageSize, pageSize);
        // 加一 是方便前端
        PageResult pageResult = PageUtils.setResult(categories, pageNum + 1, pageSize, totalPage, totalSize);
        return pageResult;
    }

    @Override
    public Category updateCategory(Category category) {

        if (category == null || category.getId() == null || category.getId() < 0 ||
                category.getName() == null || "".equals(category.getName())) {
            return null;
        }
        Long aLong = categoryMapper.updateCategory(category);
        if (aLong < 0) return null;
        else return category;
    }

    @Override
    public Long deleteCategoryById(Long id) {
        if (id == null || id < 0)return null;
        else return categoryMapper.deleteCategory(id);
    }
}
