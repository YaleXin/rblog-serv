/**
 * Author: Yalexin
 * Email: me@yalexin.top
 **/
package top.yalexin.rblog.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.yalexin.rblog.util.ParseJSONFileUtils;

@Controller
@ResponseBody
public class LinkController {
    @GetMapping("/link")
    public ResponseEntity getLinks() {
        JSONObject links = ParseJSONFileUtils.getJSONObject();
        return new ResponseEntity(links, HttpStatus.OK);
    }
}
