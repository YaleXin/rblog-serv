package top.yalexin.rblog.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.yalexin.rblog.entity.WordCloudRaw;
import top.yalexin.rblog.service.WordCloudService;

import java.util.HashMap;

@Controller
@ResponseBody
@RequestMapping("/admin/word-cloud")
public class AdminWordCloud {
    @Autowired
    WordCloudService wordCloudService;
    @PutMapping("/generate")
    public ResponseEntity generateWordCloud() {
        WordCloudRaw wordCloudRaw = wordCloudService.generateAndSaveWordCloud();
        HashMap<String, WordCloudRaw> map = new HashMap<>();
        map.put("wordCloud", wordCloudRaw);
        return new ResponseEntity(map, HttpStatus.OK);
    }
}
