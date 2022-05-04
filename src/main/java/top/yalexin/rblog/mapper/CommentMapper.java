/**
 * Author: Yalexin
 * Email: me@yalexin.top
 **/
package top.yalexin.rblog.mapper;

import org.apache.ibatis.annotations.*;
import top.yalexin.rblog.entity.Comment;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Insert("insert into t_comment(nickname,email,content,blog_id,create_time,parent_comment_id,os,browser,ip,audited,admin_comment" +
            ") values(" +
            "#{nickname},#{email},#{content},#{blogId},#{createTime},#{parentCommentId},#{OS},#{browser},#{ip},#{audited}, #{adminComment})")
    // 返回主键字段id值
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long insertComment(Comment comment);

    // 返回顶级评论
    List<Comment> findTopCommentsByParentIdAndBlogId(@Param("parentId") Long parentId, @Param("blogId") Long blogId);

    @Select("select count(id) from t_comment")
    Long countComments();

    @Update("update t_comment set audited=1 where id=#{commentId}")
    Long acceptComment(Long commentId);

    @Update("update t_comment set audited=0 where id=#{commentId}")
    Long rejectComment(Long commentId);

    @Delete("delete from t_comment where id=#{commentId}")
    Long deleteComment(Long commentId);


    List<Comment> findCategoryByInterval(Long startIndex, Long size);

    Comment findComment(Long cmtId);

    @Update("update t_comment set send_emailed=1 where id=#{cmtId}")
    Long markSentCmt(Long cmtId);

}
