package top.yalexin.rblog.service.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.yalexin.rblog.constant.AIConstant;
import top.yalexin.rblog.entity.Blog;
import top.yalexin.rblog.entity.WordCloudRaw;
import top.yalexin.rblog.mapper.WordCloudRawMapper;
import top.yalexin.rblog.service.BlogService;
import top.yalexin.rblog.service.QwenDocumentService;
import top.yalexin.rblog.service.WordCloudService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class WordCloudServiceImpl implements WordCloudService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WordCloudRawMapper wordCloudRawMapper;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private BlogService blogService;

    @Value("${qwen.api.max-batch-size}")
    private Integer maxBatchSize;

    @Value("${qwen.api.model}")
    private String model;

    @Autowired
    private QwenDocumentService qwenDocumentService;

    @Override
    public List<WordCloudRaw.WordCloudItem> getBlogsWordCloud() {
        WordCloudRaw latestWordCloudRaw = wordCloudRawMapper.findLatestWordCloudRaw();
        logger.debug("latestWordCloudRaw: {}", latestWordCloudRaw);
        latestWordCloudRaw.parseJsonToItems();
        List<WordCloudRaw.WordCloudItem> items = latestWordCloudRaw.getWordCloudItems();
        logger.debug("items: {}", items);
        return items;
    }

    /**
     * 根据大模型，返回文章词云
     */
    @Override
    public String generateWordCloud() {
        // 获取所有博客文章
        List<Blog> blogList = blogService.getBlogList();
        // 预处理博客文章内容
        List<Path> filePaths = parseBlogContent(blogList);

        // 上传文件到千问
        List<String> fileIds = qwenDocumentService.uploadMultipleDocuments(filePaths);
        String ans;
        // 解析
        try {
            ans = qwenDocumentService.analyzeDocumentsWithoutStream(fileIds);
        } catch (Exception e) {
            throw new RuntimeException("分析文档失败", e);
        }
        return ans;

    }

    @Override
    public Long saveWordCloud() {
        WordCloudRaw wordCloudRaw = new WordCloudRaw();
        // 统计耗时
        long startTime = System.currentTimeMillis();
        try{
            String wordCloudJsonSt = generateWordCloud();

            wordCloudRaw.setWordCloudJsonStr(wordCloudJsonSt);
            wordCloudRaw.parseJsonToItems();
            List<WordCloudRaw.WordCloudItem> items = wordCloudRaw.getWordCloudItems();
            if (items == null || items.isEmpty()) {
                logger.error("大模型返回的不是预期格式，返回的内容为: {}", wordCloudJsonSt);
                wordCloudRaw.setFailReason("大模型返回的不是预期格式");
            } else {
                wordCloudRaw.setFailReason(null);
            }
        }catch (Exception e) {
            wordCloudRaw.setFailReason(e.getMessage());
        }

        long endTime = System.currentTimeMillis();



        wordCloudRaw.setCreateTime(LocalDateTime.now().withNano(0));
        wordCloudRaw.setModelName(model);
        wordCloudRaw.setTokenUsage(0L);
        // 毫秒转秒
        wordCloudRaw.setTimeUsage((long) ((endTime - startTime) / 1000.0));

        Long aLong = wordCloudRawMapper.insertWordCloudRaw(wordCloudRaw);

        return aLong;
    }

    /**
     * 创建临时文件
     */
    private Path createTempFile(String prefix, String suffix, String content) {
        try {
            Path tempFile = Files.createTempFile(prefix, suffix);
            Files.writeString(tempFile, content, StandardCharsets.UTF_8);
            tempFile.toFile().deleteOnExit(); // JVM退出时删除
            logger.debug("创建临时文件: {}", tempFile);
            return tempFile;
        } catch (IOException e) {
            logger.error("创建临时文件失败: {}", e.getMessage(), e);
            throw new RuntimeException("创建临时文件失败", e);
        }
    }

    /**
     * 预处理博客文章内容，将内容写入临时文件，最多处理成
     *
     * @param blogList
     * @return
     */
    List<Path> parseBlogContent(List<Blog> blogList) {
        // 预处理文件，保证文件个数小于 MAX_UPLOAD ，目前千问对上传文件个数有限制
        // 如果博客文章数量超过最大批次大小，则多篇文章放在同一个文件中，每篇文章使用 NEW_FILE_SEPARATOR 分隔开
        List<Path> filePaths = new ArrayList<>();

        if (blogList == null || blogList.isEmpty()) {
            return filePaths;
        }

        // 如果博客数量未超过限制，每个博客单独一个文件
        if (blogList.size() <= maxBatchSize) {
            for (int i = 0; i < blogList.size(); i++) {
                Blog blog = blogList.get(i);
                logger.debug("add blogID: {}", blog.getId());
                String content = blog.getContent();
                Path tempFile = createTempFile("blog_" + i + "_", ".md", content);
                filePaths.add(tempFile);
            }
        } else {
            // 超过限制，合并多个博客到同一个文件
            int filesNeeded = maxBatchSize;
            int blogsPerFile = (int) Math.ceil((double) blogList.size() / filesNeeded);

            for (int fileIndex = 0; fileIndex < filesNeeded; fileIndex++) {
                StringBuilder mergedContent = new StringBuilder();
                int startIdx = fileIndex * blogsPerFile;
                int endIdx = Math.min(startIdx + blogsPerFile, blogList.size());

                for (int i = startIdx; i < endIdx; i++) {
                    Blog blog = blogList.get(i);
                    logger.debug("add blogID: {}", blog.getId());
                    String blogContent = blog.getContent();

                    if (i > startIdx) {
                        mergedContent.append(AIConstant.NEW_FILE_SEPARATOR);
                    }
                    mergedContent.append(blogContent);
                }
                Path tempFile = createTempFile("blog_" + fileIndex + "_", ".md", mergedContent.toString());
                filePaths.add(tempFile);
            }
        }

        logger.info("生成了 {} 个文件用于处理 {} 篇博客", filePaths.size(), blogList.size());
        return filePaths;
    }

}
