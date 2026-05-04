package top.yalexin.rblog.service;

import top.yalexin.rblog.entity.WordCloudRaw;

import java.util.List;

public interface WordCloudService {
     List<WordCloudRaw.WordCloudItem> getBlogsWordCloud();
}
