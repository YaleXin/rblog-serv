/**
 * Author: Yalexin
 * Email: 181303209@yzu.edu.cn
 **/
package top.yalexin.rblog.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.data.domain.Pageable;
import top.yalexin.rblog.entity.Blog;

import java.util.List;

@Mapper
public interface BlogMapper {
    @Select("select * from t_blog")
    List<Blog> findAllBlog();

    @Select("select * from t_blog")
    List<Blog> findAllBlogPage();

//    @Select("select * from t_blog where id=#{id}")
    Blog findBlog(long id);

    @Insert("insert into t_blog(name,content,description, create_time, update_time,category_id) values" +
            "(#{name},#{content},#{description}, #{createTime}, #{updateTime}, #{category.id})")
    // 返回主键字段id值
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long insertBlog(Blog blog);

    @Insert("insert into t_blog_tags(blogs_id,tags_id) values(#{blogId}, #{tagId})")
    void insertBlogTag(Long blogId, Long tagId);
}
