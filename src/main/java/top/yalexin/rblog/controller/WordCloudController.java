package top.yalexin.rblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.yalexin.rblog.constant.CacheNameConstant;
import top.yalexin.rblog.entity.WordCloud;
import top.yalexin.rblog.entity.WordCloudRaw;
import top.yalexin.rblog.service.WordCloudService;

import java.util.HashMap;
import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/wordCloud")
public class WordCloudController {
    @Autowired
    WordCloudService wordCloudService;
    @GetMapping("/all")
    @Cacheable(value = CacheNameConstant.WORD_CLOUD_ALL)
    public ResponseEntity getAllBlogWordCloud(){
        List<WordCloudRaw.WordCloudItem> blogsWordCloud = wordCloudService.getBlogsWordCloud();
        HashMap<String, Object> map = new HashMap<>();
        map.put("wordCloud", blogsWordCloud);
        return new ResponseEntity(map, HttpStatus.OK);
    }
}
