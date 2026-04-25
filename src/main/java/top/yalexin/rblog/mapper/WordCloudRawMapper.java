package top.yalexin.rblog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import top.yalexin.rblog.entity.WordCloudRaw;

@Component(value = "WordCloudMapper")
@Mapper
public interface WordCloudRawMapper {
    @Select("select * from t_word_cloud order by create_time desc limit 1")
    @ResultMap("top.yalexin.rblog.mapper.WordCloudRawMapper.answerMap")
    WordCloudRaw findLatestWordCloudRaw();
}
