package top.yalexin.rblog.apsect;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.yalexin.rblog.annotation.SentinelIpLimiter;
import top.yalexin.rblog.util.IPUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Author: Yalexin
 * SentinelIpLimiter 注解的切面逻辑
 */
@Aspect
@Component
public class SentinelLimiterAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Around("@annotation(sentinelLimiter)")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint, SentinelIpLimiter sentinelLimiter) throws Throwable {
        // 获取注解的属性值
        String resourceName = sentinelLimiter.value();
        EntryType entryType = sentinelLimiter.entryType();
        int batchCount = sentinelLimiter.batchCount();

        // 获取当前请求的属性
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            // 从请求属性中获取HttpServletRequest对象
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            // 获取 IP
            String remoteAddr = IPUtils.getIRealIPAddr(request);
            Entry entry = null;
            try {
                // 尝试获取资源
                entry = SphU.entry(resourceName, entryType, batchCount, remoteAddr);
                // 如果成功获得资源，则继续执行目标方法
                return joinPoint.proceed();

            } catch (Throwable ex) {
                logger.debug("block by "+ex.getMessage());
                // 业务异常
                if (!BlockException.isBlockException(ex)) {
                    Tracer.trace(ex);
                    logger.error(ex.getMessage());
                    return new ResponseEntity(null, HttpStatus.OK);
                }
                // 降级操作
                if (ex instanceof DegradeException) {
                    // 处理降级逻辑
                    logger.warn(ex.getMessage());
                    return new ResponseEntity(null, HttpStatus.OK);
                }
                // 限流操作，直接返回 HttpStatus.TOO_MANY_REQUESTS
                return new ResponseEntity(null, HttpStatus.TOO_MANY_REQUESTS);
            } finally {
                if (entry != null) {
                    entry.exit(1, remoteAddr);
                }
            }
        }
        // 如果没有匹配到Servlet请求，让其继续执行（或根据需要返回默认响应）
        return joinPoint.proceed();
    }
}
