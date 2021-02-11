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

    @Select("select count(id) from t_category")
    Long countCategory();

    @Select("select * from t_category limit #{startIndex},#{size}")
    List<Category> findCategoryByInterval(Long startIndex, Long size);


    @Update("update t_category set name=#{name} where id=#{id}")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long updateCategory(Category category);

    @Delete("delete from t_category where id=#{categoryId}")
    Long deleteCategory(Long categoryId);


}
