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
import top.yalexin.rblog.entity.Tag;
import top.yalexin.rblog.service.TagService;
import top.yalexin.rblog.util.PageResult;

import java.util.HashMap;

@Controller
@ResponseBody
@RequestMapping("/admin/tag")
public class AdminTagController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TagService tagService;

    @GetMapping("/tagPage")
    ResponseEntity getTagPage(@RequestParam(value = "pageNum", required = false, defaultValue = "1") String pageNum,
                                   @RequestParam(value = "pageSize", required = false, defaultValue = "1") String pageSize) {
        long pageNumL = Long.parseLong(pageNum);
        long pageSizeL = Long.parseLong(pageSize);
        PageResult tagByPage = tagService.getTagByPage(pageNumL, pageSizeL);
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", tagByPage);
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @PutMapping("/modify")
    ResponseEntity modifyTag(@RequestBody HashMap json) {
        Tag tag = JSON.parseObject(json.get("data").toString(), Tag.class);
        logger.debug("前端tag-----> {}", tag);
        HashMap<String, Object> map = new HashMap<>();
        Tag newTag = tagService.updateTag(tag);
        logger.debug("after editTag-----> {}", newTag);
        if (newTag == null) return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        map.put("newTag", newTag);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity deleteTag(@PathVariable("id") Long id) {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("result", tagService.deleteTagById(id));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }


}
