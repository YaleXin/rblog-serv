/**
 * Author: Yalexin
 * Email: me@yalexin.top
 **/
package top.yalexin.rblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import top.yalexin.rblog.service.ArchiveService;

import java.util.Map;

@ResponseBody
@RestController

public class ArchiveController {
    @Autowired
    private ArchiveService archiveService;

    @GetMapping("/archive")
    public ResponseEntity getBlogs() {
        Map map = archiveService.getBlogListWithYearMonth();
        return new ResponseEntity(map, HttpStatus.OK);
    }
}
