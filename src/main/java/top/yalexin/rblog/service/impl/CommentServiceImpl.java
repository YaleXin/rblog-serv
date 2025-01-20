/**
 * Author: Yalexin
 * Email: me@yalexin.top
 **/
package top.yalexin.rblog.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.yalexin.rblog.entity.Category;
import top.yalexin.rblog.entity.Comment;
import top.yalexin.rblog.entity.User;
import top.yalexin.rblog.mapper.CommentMapper;
import top.yalexin.rblog.service.CommentService;
import top.yalexin.rblog.service.SendEmailService;
import top.yalexin.rblog.util.IPUtils;
import top.yalexin.rblog.util.PageResult;
import top.yalexin.rblog.util.PageUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Transactional
@Service
public class CommentServiceImpl implements CommentService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    SendEmailService sendEmailService;

    @Autowired
    CommentMapper commentMapper;

    /**
     * @param comment
     * @param request
     * @return 0：写入数据库失败，1：写入成功，2：时间间隔过小
     */
    @Override
    public int addComment(Comment comment, HttpServletRequest request) {
        if (commentMapper == null) return 0;
//        获取 IP 地址
        String iRealIPAddr = IPUtils.getIRealIPAddr(request);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        // 若是管理员评论，直接不需要审核和接收频率限制
        if (user != null) {
            comment.setIp("127.0.0.1");
            comment.setAdminComment(true);
            comment.setAudited(true);
            comment.setNickname(user.getNickname());
            comment.setEmail(user.getEmail());
            Long result = commentMapper.insertComment(comment);
            if (result == null || result <= 0) return 0;
            Long parentCommentId = comment.getParentCommentId();
            Comment parentCmt = commentMapper.findComment(parentCommentId);
            if (parentCmt != null) {
                sendEmailService.send(parentCmt, comment, true);
            }
            return 1;
        } else { // 不是管理员就看上次发表评论的时间
            Date lastCmtTime = (Date) session.getAttribute("lastCmtTime");
            Date now = new Date();
            // 假如之前没有评论过，则直接可以评论
            if (lastCmtTime == null) {
                comment.setIp(iRealIPAddr);
                Long result = commentMapper.insertComment(comment);
                if (result == null || result <= 0) return 0;
                else {
                    session.setAttribute("lastCmtTime", now);
                    // 评论成功，发送邮件给管理员
                    Comment parentCmt = null;
                    Long parentCommentId = comment.getParentCommentId();
                    if (parentCommentId != null || parentCommentId > 0) {
                        parentCmt = commentMapper.findComment(comment.getParentCommentId());
                    }
                    sendEmailService.send(parentCmt, comment, false);
                    return 1;
                }
            } else {
                long dist = now.getTime() - lastCmtTime.getTime();
                // 若上一分钟刚刚提交过
                if (dist < 60 * 1000) {
                    return 2;
                }
                comment.setIp(iRealIPAddr);
                Long result = commentMapper.insertComment(comment);
                if (result == null || result <= 0) return 0;
                else {
                    session.setAttribute("lastCmtTime", now);
                    // 评论成功，发送邮件给管理员
                    Comment parentCmt = null;
                    Long parentCommentId = comment.getParentCommentId();
                    if (parentCommentId != null || parentCommentId > 0) {
                        parentCmt = commentMapper.findComment(comment.getParentCommentId());
                    }
                    sendEmailService.send(parentCmt, comment, false);
                    return 1;
                }
            }


        }
    }

    @Override
    public List<Comment> getTopCommentsByBlogId(Long blogId) {
        if (blogId == null || blogId < 0) return null;
        List<Comment> rawCmts = commentMapper.findTopCommentsByParentIdAndBlogId((long) -1, blogId);
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

    // 通过审核
    @Override
    public Long acceptCommentByCommentId(Long commentId) {
        if (commentId == null || commentId < 0) return null;
        // 标记为通过审核状态
        Long aLong = commentMapper.acceptComment(commentId);
        Comment replyCmt = commentMapper.findComment(commentId);
        if (aLong > 0 && !replyCmt.isSendEmailed()) {
            Comment parentCmt = commentMapper.findComment(replyCmt.getParentCommentId());
            if (parentCmt != null)
                sendEmailService.send(parentCmt, replyCmt, true);
        }
        return aLong;
    }

    // "下架"
    @Override
    public Long rejectCommentByCommentId(Long commentId) {
        if (commentId == null || commentId < 0) return null;
        return commentMapper.rejectComment(commentId);
    }

    @Override
    public Long deleteCommentByCommentId(Long commentId) {
        if (commentId == null || commentId < 0) return null;
        return commentMapper.deleteComment(commentId);
    }

    @Override
    public Long markCommentSent(Long commentId) {
        if (commentId == null || commentId < 0) return null;
        return commentMapper.markSentCmt(commentId);
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
