/**
 * Author: Yalexin
 * Email: me@yalexin.top
 **/
package top.yalexin.rblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.yalexin.rblog.entity.Tag;
import top.yalexin.rblog.service.TagService;

import java.util.HashMap;
import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private TagService tagService;
    @GetMapping("/all")

    HashMap getAllTags(){
        List<Tag> tags = tagService.getTagList();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("tags", tags);
        return hashMap;
    }
}
