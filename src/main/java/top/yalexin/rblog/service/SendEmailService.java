/**
 * Author: Yalexin
 * Email: 181303209@yzu.edu.cn
 **/
package top.yalexin.rblog.service;

import top.yalexin.rblog.entity.Comment;

public interface SendEmailService {
    void send(Comment parentComment, Comment replyComment,boolean is2Parent);
}
