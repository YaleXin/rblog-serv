package top.yalexin.rblog.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@MapperScan("top.yalexin.rblog.mapper") // 扫描Mapper接口所在包
public class MyBatisConfig {
}