/**
 * Author: Yalexin
 * Email: 181303209@yzu.edu.cn
 **/
package top.yalexin.rblog.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import top.yalexin.rblog.entity.Category;
import top.yalexin.rblog.entity.Tag;

import java.util.List;

@Mapper

@Component(value = "TagMapper")
public interface TagMapper {

    List<Tag> findAllTagsWithBlogCnt();

    @Insert("insert into t_tag(name) values(#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long insertTag(Tag tag);

    @Select("select count(id) from t_tag")
    Long countTag();

    @Select("select * from t_tag limit #{startIndex},#{size}")
    List<Tag> findTagByInterval(Long startIndex, Long size);

    @Update("update t_tag set name=#{name} where id=#{id}")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long updateTag(Tag tag);

    @Delete("delete from t_tag where id=#{tagId}")
    Long deleteTag(Long tagId);
}
