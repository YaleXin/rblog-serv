package top.yalexin.rblog.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.yalexin.rblog.entity.WordCloud;
import top.yalexin.rblog.service.WordCloudService;

import java.util.Arrays;
import java.util.List;
@Transactional
@Service
public class WordCloudServiceImpl  implements WordCloudService {

    @Override
    public List<WordCloud> getBlogsWordCloud() {
        List<WordCloud> wordCloudList = Arrays.asList(
                new WordCloud("Web Technologies", 26),
                new WordCloud("HTML", 20),
                new WordCloud("canvas", 20),
                new WordCloud("CSS", 15),
                new WordCloud("JavaScript Node", 15),
                new WordCloud("Document Object Model", 12),
                new WordCloud("audio", 12),
                new WordCloud("video", 12),
                new WordCloud("Web Workers", 12),
                new WordCloud("XMLHttpRequest", 12),
                new WordCloud("SVG", 12),
                new WordCloud("JSON.parse()", 9),
                new WordCloud("Geolocation", 9),
                new WordCloud("data attribute", 9),
                new WordCloud("transform", 9),
                new WordCloud("transition", 9),
                new WordCloud("animation", 9),
                new WordCloud("setTimeout", 7),
                new WordCloud("@font-face", 7),
                new WordCloud("Typed Arrays", 7),
                new WordCloud("FileReader API", 7),
                new WordCloud("FormData", 7),
                new WordCloud("IndexedDB", 7),
                new WordCloud("getUserMedia()", 7),
                new WordCloud("postMassage()", 7),
                new WordCloud("CORS", 7),
                new WordCloud("strict mode", 6),
                new WordCloud("calc()", 6),
                new WordCloud("supports()", 6),
                new WordCloud("media queries", 6),
                new WordCloud("full screen", 6),
                new WordCloud("notification", 6),
                new WordCloud("orientation", 6),
                new WordCloud("requestAnimationFrame", 6),
                new WordCloud("border-radius", 5),
                new WordCloud("box-sizing", 5),
                new WordCloud("rgba()", 5),
                new WordCloud("text-shadow", 5),
                new WordCloud("box-shadow", 5),
                new WordCloud("flexbox", 5),
                new WordCloud("viewpoint", 5)
        );
        return wordCloudList;
    }
}
