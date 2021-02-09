/**
 * Author: Yalexin
 * Email: 181303209@yzu.edu.cn
 **/
package top.yalexin.rblog.mapper;

import org.apache.ibatis.annotations.*;
import top.yalexin.rblog.entity.Category;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @Select("select * from t_category")
    List<Category> findAllCategories();

    @Insert("insert into t_category(name) values(#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    public Long insertCategory(Category category);
}
