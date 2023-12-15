/**
 * Author: Yalexin
 * Email: 181303209@yzu.edu.cn
 **/
package top.yalexin.rblog.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import top.yalexin.rblog.entity.Blog;

import java.util.List;

@Mapper
@Component(value = "BlogMapper")
public interface BlogMapper {
    @Select("select * from t_blog")
    List<Blog> findAllBlog();

    Blog findBlog(long id);

    //****************       新增文章时有可能同时新增标签         **************

    @Insert("insert into t_blog(name,content,description, create_time, update_time,category_id,top) values" +
            "(#{name},#{content},#{description}, #{createTime}, #{updateTime}, #{category.id}, #{top})")
    // 返回主键字段id值
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long insertBlog(Blog blog);

    @Insert("insert into t_blog_tags(blogs_id,tags_id) values(#{blogId}, #{tagId})")
    Long insertBlogTag(Long blogId, Long tagId);

    //************************** 更改文章时有可能同时新增标签 *************************8


    @Update("update t_blog set name=#{name},content=#{content},description=#{description}, create_time=#{createTime}, update_time=#{updateTime}, category_id=#{category.id}, top=#{top} where id=#{id}")
    // 返回主键字段id值
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long updateBlog(Blog blog);


    @Delete("DELETE FROM t_blog_tags WHERE blogs_id=#{blogId}")
    Long emptyBlogTags(Long blogId);

    //****************************
//分页查询
    List<Blog> findBlogByInterval(Long startIndex, Long size);

    @Select("select count(id) from t_blog")
    Long countBlog();

    @Delete("delete from t_blog where id=#{id}")
    Long deleteBlog(Long id);

    //**************   根据分页进行选取某个分类下的文章  *****************
    @Select("select count(id) from t_blog where category_id=#{categoryId}")
    Long countBlogByCategory(Long categoryId);

    List<Blog> findBlogWithCategoryByInterval(Long startIndex, Long size, Long categoryId);

    //********************************
//**************   根据分页进行选取某个标签下的文章  *****************
    @Select("select count(blogs_id) from t_blog_tags where tags_id=#{tagId}")
    Long countBlogByTag(Long tagId);

    List<Blog> findBlogWithTagByInterval(Long startIndex, Long size, Long tagId);
//********************************


    //    *****************   查询指定年份对应的文章  *******************
    @Select("select blog.name,blog.create_time createTime, blog.id id from t_blog blog where date_format(blog.create_time, '%Y')=#{year} order by blog.create_time desc")
    List<Blog> findBlogByYear(Long year);


    //    *****************  根据内容或者标题进行模糊分页查询 *******************
    @Select("SELECT count(id) FROM t_blog where name like #{nameOrContent} or content like #{nameOrContent}")
    Long countBlogLikeNameOrContent(String nameOrContent);

    List<Blog> findBlogByIntervalAndNameOrContent(Long startIndex, Long size, String nameOrContent);

    //********************************************************************************************
    @Update("update t_blog set views=views+1 where id=#{blogId}")
    Long updateBlogViews(Long blogId);

    //******* 获取所有文章对应的年份  *********
    List<Long>getAllYears();

    //******* 获取置顶文章  *********
    List<Blog> findTopBlog();
}
