/**
 * Author: Yalexin
 * Email: 181303209@yzu.edu.cn
 **/
package top.yalexin.rblog.mapper;

import org.apache.ibatis.annotations.*;
import top.yalexin.rblog.entity.Tag;

import java.util.List;

@Mapper
public interface TagMapper {
    @Select("select * from t_tag")
    List<Tag> findAllTags();

    @Insert("insert into t_tag(name) values(#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long insertTag(Tag tag);
}
