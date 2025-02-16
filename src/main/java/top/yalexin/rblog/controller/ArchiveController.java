/**
 * Author: Yalexin
 * Email: me@yalexin.top
 **/
package top.yalexin.rblog.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.yalexin.rblog.constant.CacheNameConstant;
import top.yalexin.rblog.service.ArchiveService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ResponseBody
@RestController

public class ArchiveController {
    @Autowired
    private ArchiveService archiveService;

    @PostMapping("/archive")
    @Cacheable(value = CacheNameConstant.ARCHIVE_CACHE,key = "#json.get('data')")
    public ResponseEntity getBlogs(@RequestBody HashMap json) {
        Long year = JSON.parseObject(json.get("data").toString(), Long.class);
        Map map = archiveService.getBlogListByYear(year);
        return new ResponseEntity(map, HttpStatus.OK);
    }
    @GetMapping("/archive/years")
    @Cacheable(value = CacheNameConstant.ARCHIVE_CACHE)
    public ResponseEntity getYears() {
        HashMap<String, List<Long>> map = new HashMap<>();
        map.put("years", archiveService.getAllyears());
        return new ResponseEntity(map, HttpStatus.OK);
    }
}
