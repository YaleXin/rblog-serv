/**
 * Author: Yalexin
 * Email: me@yalexin.top
 **/
package top.yalexin.rblog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.yalexin.rblog.entity.Category;
import top.yalexin.rblog.entity.Comment;
import top.yalexin.rblog.mapper.CommentMapper;
import top.yalexin.rblog.util.PageResult;
import top.yalexin.rblog.util.PageUtils;

import java.util.LinkedList;
import java.util.List;

@Transactional
@Service
public class CommentServiceImpl implements CommentService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    CommentMapper commentMapper;

    @Override
    public Comment addComment(Comment comment) {
        if (commentMapper == null) return null;
        Long result = commentMapper.insertComment(comment);
        if (result == null || result < 0) return null;
        return comment;
    }

    @Override
    public List<Comment> getTopCommentsByBlogId(Long blogId) {
        if (blogId == null || blogId < 0) return null;
        List<Comment> rawCmts = commentMapper.findTopCommentsByParentIdAndBlogId((long) -1, blogId);
        logger.info("rwadCmts = {}", rawCmts);
        return getParent(rawCmts);
    }

    @Override
    public PageResult getCommentByPage(Long requestPageNum, Long requestPageSize) {
        Long totalSize = commentMapper.countComments();
        // 前端传来的 size 若为非正数，则默认 size=1
        long pageSize = Math.max(requestPageSize, 1);
        // 前端传来的页面编号若非数， 则默认 请求第一页
        long pageNum = requestPageNum > 0 ? (requestPageNum - 1) : 0;

        long totalPage = totalSize / pageSize;

        List<Comment> comments = commentMapper.findCategoryByInterval(pageNum * pageSize, pageSize);

        PageResult pageResult = PageUtils.setResult(comments, pageNum + 1, pageSize, totalPage, totalSize);
        return pageResult;
    }

    @Override
    public Long acceptCommentByCommentId(Long commentId) {
        if(commentId == null || commentId < 0)return null;
        return commentMapper.acceptComment(commentId);
    }

    @Override
    public Long rejectCommentByCommentId(Long commentId) {
        if(commentId == null || commentId < 0)return null;
        return commentMapper.rejectComment(commentId);
    }

    @Override
    public Long deleteCommentByCommentId(Long commentId) {
        if(commentId == null || commentId < 0)return null;
        return commentMapper.deleteComment(commentId);
    }

    private List<Comment> getParent(List<Comment> rawComments) {
        // 对于每一个顶级回复
        for (Comment topComment : rawComments) {
            // 将顶级评论的子孙评论归结到一个集合中
            LinkedList<Comment> comments = new LinkedList<>();
            List<Comment> replyCmtsByTopCmt = topComment.getReplyComments();
            for (Comment replyComment : replyCmtsByTopCmt) {
                handleChild(replyComment, comments);
//                replyComment.setReplyNickname(topComment.getId().toString());
                replyComment.setReplyNickname(topComment.getNickname());
            }
            topComment.setReplyComments(comments);
        }
        return rawComments;
    }

    //处理二级评论以及子评论
    private void handleChild(Comment replyComment, List<Comment> parent) {
        List<Comment> grandchildren = replyComment.getReplyComments();
        replyComment.setReplyComments(null);
        parent.add(replyComment);
        for (Comment grandChild : grandchildren) {
//            grandChild.setReplyNickname(replyComment.getId().toString());
            grandChild.setReplyNickname(replyComment.getNickname());
            if (grandChild.getReplyComments() != null) handleChild(grandChild, parent);
        }
    }
}
