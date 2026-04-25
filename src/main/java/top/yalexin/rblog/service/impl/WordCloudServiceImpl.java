package top.yalexin.rblog.service.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.yalexin.rblog.entity.WordCloudRaw;
import top.yalexin.rblog.mapper.WordCloudRawMapper;
import top.yalexin.rblog.service.WordCloudService;

import java.util.List;
@Transactional
@Service
public class WordCloudServiceImpl  implements WordCloudService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WordCloudRawMapper wordCloudRawMapper;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<WordCloudRaw.WordCloudItem> getBlogsWordCloud() {
        WordCloudRaw latestWordCloudRaw = wordCloudRawMapper.findLatestWordCloudRaw();
        logger.debug("latestWordCloudRaw: {}", latestWordCloudRaw);
        latestWordCloudRaw.parseJsonToItems();
        List<WordCloudRaw.WordCloudItem> items = latestWordCloudRaw.getWordCloudItems();
        logger.debug("items: {}", items);
       return items;
    }
}
