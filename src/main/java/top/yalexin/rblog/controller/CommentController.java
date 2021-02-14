/**
 * Author: Yalexin
 * Email: me@yalexin.top
 **/
package top.yalexin.rblog.controller;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.yalexin.rblog.entity.Blog;
import top.yalexin.rblog.entity.Comment;
import top.yalexin.rblog.service.CommentService;

import java.util.HashMap;
import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/comment")
public class CommentController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CommentService commentService;

    @PutMapping("/add")
    ResponseEntity addComment(@RequestBody HashMap json) {
        Comment comment = JSON.parseObject(json.get("data").toString(), Comment.class);
       logger.debug("put comment ---------> {}", comment);
        HashMap<String, Object> map = new HashMap<>();
        Comment addComment = commentService.addComment(comment);
        if (addComment == null) return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        map.put("addComment", addComment);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/{blogId}")
    ResponseEntity getCommentsByBlogId(@PathVariable("blogId") Long blogId) {
        HashMap<String, Object> map = new HashMap<>();
        List<Comment> comments = commentService.getTopCommentsByBlogId(blogId);
        map.put("comments", comments);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
