/**
 * Author: Yalexin
 * Email: me@yalexin.top
 **/
package top.yalexin.rblog.controller.admin;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.yalexin.rblog.entity.Blog;
import top.yalexin.rblog.service.CommentService;
import top.yalexin.rblog.util.PageResult;

import java.util.HashMap;

@Controller
@ResponseBody
@RequestMapping("/admin/comment")
public class AdminCommentController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private CommentService commentService;

    @GetMapping("/commentPage")
    ResponseEntity getCommentPage(@RequestParam(value = "pageNum", required = false, defaultValue = "1") String pageNum,
                                  @RequestParam(value = "pageSize", required = false, defaultValue = "1") String pageSize) {
        long pageNumL = Long.parseLong(pageNum);
        long pageSizeL = Long.parseLong(pageSize);
        PageResult tagByPage = commentService.getCommentByPage(pageNumL, pageSizeL);
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", tagByPage);
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @PutMapping("/accept")
    ResponseEntity acceptComment(@RequestBody HashMap json) {
        Long id = JSON.parseObject(json.get("data").toString(), Long.class);
        Long result = commentService.acceptCommentByCommentId(id);
        HashMap<String, Long> map = new HashMap<>();
        map.put("result", result);
        return new ResponseEntity(map, HttpStatus.OK);
    }


    @PutMapping("/reject")
    ResponseEntity rejectComment(@RequestBody HashMap json) {
        logger.info("json = " + json);
        Long id = JSON.parseObject(json.get("data").toString(), Long.class);
        Long result = commentService.rejectCommentByCommentId(id);
        HashMap<String, Long> map = new HashMap<>();
        map.put("result", result);
        return new ResponseEntity(map, HttpStatus.OK);
    }


    @DeleteMapping("/delete")
    ResponseEntity deleteComment(@RequestBody HashMap json) {
        logger.info("json = {}", json);
        Long id = JSON.parseObject(json.get("id").toString(), Long.class);
        Long result = commentService.deleteCommentByCommentId(id);
        HashMap<String, Long> map = new HashMap<>();
        map.put("result", result);
        return new ResponseEntity(map, HttpStatus.OK);
    }


}
