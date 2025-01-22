package top.yalexin.rblog.annotation;

import com.alibaba.csp.sentinel.EntryType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Yalexin
 * 用于基于 IP 限流
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SentinelIpLimiter {
    // 资源名字
    String value() default "";
    // 类型
    EntryType entryType() default EntryType.OUT;

    int batchCount () default 1;
}
