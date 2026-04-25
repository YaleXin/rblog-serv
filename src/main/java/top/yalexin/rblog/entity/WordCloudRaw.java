package top.yalexin.rblog.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 词云记录实体类
 * 对应数据库表：t_word_cloud
 */
public class WordCloudRaw {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    // 对应字段：id (bigint)
    private Long id;

    // 对应字段：word_cloud_json_str (longtext)
    private String wordCloudJsonStr;

    // 内部类，对应 JSON 数组中的对象结构
    public static class WordCloudItem {
        private String word;
        private Integer weight;

        // 必须有无参构造
        public WordCloudItem() {}

        // Getter & Setter
        public String getWord() { return word; }
        public void setWord(String word) { this.word = word; }
        public Integer getWeight() { return weight; }
        public void setWeight(Integer weight) { this.weight = weight; }
    }

    private List<WordCloudItem> wordCloudItems;

    // 对应字段：create_time (datetime)
    private LocalDateTime createTime;

    // 对应字段：model_name (varchar)
    private String modelName;

    // 对应字段：token_usage (bigint)
    private Long tokenUsage;

    // 对应字段：time_usage (bigint)
    private Long timeUsage;

    // 对应字段：fail_reason (text)
    private String failReason;

    // --- 默认无参构造方法 ---
    public WordCloudRaw() {
    }

    // --- 全参构造方法 ---
    public WordCloudRaw(Long id, String wordCloudJsonStr, LocalDateTime createTime, String modelName, Long tokenUsage, Long timeUsage, String failReason) {
        this.id = id;
        this.wordCloudJsonStr = wordCloudJsonStr;
        this.createTime = createTime;
        this.modelName = modelName;
        this.tokenUsage = tokenUsage;
        this.timeUsage = timeUsage;
        this.failReason = failReason;
    }

    // --- Getter 和 Setter 方法 ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWordCloudJsonStr() {
        return wordCloudJsonStr;
    }

    public void setWordCloudJsonStr(String wordCloudJsonStr) {
        this.wordCloudJsonStr = wordCloudJsonStr;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Long getTokenUsage() {
        return tokenUsage;
    }

    public void setTokenUsage(Long tokenUsage) {
        this.tokenUsage = tokenUsage;
    }

    public Long getTimeUsage() {
        return timeUsage;
    }

    public void setTimeUsage(Long timeUsage) {
        this.timeUsage = timeUsage;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public List<WordCloudItem> getWordCloudItems() {
        return wordCloudItems;
    }

    public void setWordCloudItems(List<WordCloudItem> wordCloudItems) {
        this.wordCloudItems = wordCloudItems;
    }

    @Override
    public String toString() {
        return "WordCloudRaw{" +
                "id=" + id +
                ", wordCloudJsonStr='" + wordCloudJsonStr + '\'' +
                ", wordCloudItems=" + wordCloudItems +
                ", createTime=" + createTime +
                ", modelName='" + modelName + '\'' +
                ", tokenUsage=" + tokenUsage +
                ", timeUsage=" + timeUsage +
                ", failReason='" + failReason + '\'' +
                '}';
    }


    // --- 添加JSON与实体类转换方法 ---
    /**
     * 将wordCloudJsonStr解析为WordCloudItem列表
     */
    public void parseJsonToItems() {
        if (wordCloudJsonStr == null || wordCloudJsonStr.isEmpty()) {
            return;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.wordCloudItems = objectMapper.readValue(wordCloudJsonStr, new TypeReference<List<WordCloudItem>>() {});
        } catch (JsonProcessingException e) {
            logger.error("解析JSON字符串为WordCloudItem列表时出错", e);
            this.wordCloudItems = null;
        }
    }

    /**
     * 将WordCloudItem列表序列化为JSON字符串
     */
    public void generateJsonFromItems() {
        if (wordCloudItems == null || wordCloudItems.isEmpty()) {
            this.wordCloudJsonStr = "[]";
            return;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.wordCloudJsonStr = objectMapper.writeValueAsString(wordCloudItems);
        } catch (JsonProcessingException e) {
            // 实际应用中建议替换为日志记录
            logger.error("序列化WordCloudItem列表为JSON字符串时出错", e);
            this.wordCloudJsonStr = "[]";
        }
    }
    // --- 转换方法结束 ---


}