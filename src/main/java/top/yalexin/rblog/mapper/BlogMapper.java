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

    Blog findBlog(long id);

    //------------------------

    @Insert("insert into t_blog(name,content,description, create_time, update_time,category_id) values" +
            "(#{name},#{content},#{description}, #{createTime}, #{updateTime}, #{category.id})")
    // 返回主键字段id值
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long insertBlog(Blog blog);

    @Insert("insert into t_blog_tags(blogs_id,tags_id) values(#{blogId}, #{tagId})")
    Long insertBlogTag(Long blogId, Long tagId);

    //--------------------------


    @Update("update t_blog set name=#{name},content=#{content},description=#{description}, create_time=#{createTime}, update_time=#{updateTime}, category_id=#{category.id} where id=#{id}")
    // 返回主键字段id值
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long updateBlog(Blog blog);


    @Delete("DELETE FROM t_blog_tags WHERE blogs_id=#{blogId}")
    Long emptyBlogTags(Long blogId);

    //---------------

    List<Blog> findBlogByInterval(Long startIndex, Long size);

    @Select("select count(id) from t_blog")
    Long countBlog();

    @Delete("delete from t_blog where id=#{id}")
    Long deleteBlog(Long id);


}
