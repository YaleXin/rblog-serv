/**
 * Author: Yalexin
 * Email: me@yalexin.top
 **/
package top.yalexin.rblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.yalexin.rblog.entity.Tag;
import top.yalexin.rblog.service.BlogService;
import top.yalexin.rblog.service.TagService;
import top.yalexin.rblog.util.PageResult;

import java.util.HashMap;
import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private TagService tagService;
    @Autowired
    private BlogService blogService;

    @GetMapping("/all")
    ResponseEntity getAllTags() {
        List<Tag> tags = tagService.getTagListWithBlogCnt();
        HashMap<String, Object> map = new HashMap<>();
        map.put("tags", tags);
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @GetMapping("/{tagId}")
    ResponseEntity getPageBlogByTag(@PathVariable("tagId") Long tagId,
                                    @RequestParam(value = "pageNum", required = false, defaultValue = "1") String pageNum,
                                    @RequestParam(value = "pageSize", required = false, defaultValue = "1") String pageSize) {
        long pageNumL = Long.parseLong(pageNum);
        long pageSizeL = Long.parseLong(pageSize);

        PageResult pageResult = blogService.getBlogByPageAndTagId(pageNumL, pageSizeL, tagId);
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", pageResult);
        return new ResponseEntity(map, HttpStatus.OK);
    }


}
