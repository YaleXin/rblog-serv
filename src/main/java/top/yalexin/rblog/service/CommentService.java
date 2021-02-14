/**
 * Author: Yalexin
 * Email: me@yalexin.top
 **/
package top.yalexin.rblog.service;

import top.yalexin.rblog.entity.Comment;
import top.yalexin.rblog.util.PageResult;

import java.util.List;

public interface CommentService {
    Comment addComment(Comment comment);

    List<Comment> getTopCommentsByBlogId(Long blogId);

    PageResult getCommentByPage(Long pageNum, Long pageSize);

    Long acceptCommentByCommentId(Long commentId);

    Long rejectCommentByCommentId(Long commentId);

    Long deleteCommentByCommentId(Long commentId);

}
