package top.yalexin.rblog.mapper;

import org.apache.ibatis.annotations.*;
import top.yalexin.rblog.entity.WordCloudRaw;
@Mapper
public interface WordCloudRawMapper {
    @Select("select * from t_word_cloud order by create_time desc limit 1")
    @ResultMap("top.yalexin.rblog.mapper.WordCloudRawMapper.answerMap")
    WordCloudRaw findLatestWordCloudRaw();

    @Insert("insert into t_word_cloud (word_cloud_json_str, create_time, model_name, token_usage, time_usage, fail_reason) values (#{wordCloudJsonStr}, #{createTime}, #{modelName}, #{tokenUsage}, #{timeUsage}, #{failReason})")
    // 返回主键字段id值
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long insertWordCloudRaw(WordCloudRaw wordCloudRaw);
}
