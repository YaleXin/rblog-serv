/**
 * Author: Yalexin
 * Email: me@yalexin.top
 **/
package top.yalexin.rblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.yalexin.rblog.entity.Link;

import top.yalexin.rblog.service.LinkService;

import java.util.HashMap;
import java.util.List;

@Controller
@ResponseBody
public class LinkController {
    @Autowired
    LinkService linkService;

    @GetMapping("/link")
    public ResponseEntity getLinks()  {
        HashMap<String, Object> map = new HashMap<>();
        List<Link> links = linkService.getAllLink();
        map.put("links", links);
        return new ResponseEntity(map, HttpStatus.OK);
    }
}
