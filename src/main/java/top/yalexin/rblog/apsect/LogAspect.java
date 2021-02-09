/**
 * Author: Yalexin
 * Email: me@yalexin.top
 **/
package top.yalexin.rblog.apsect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
@Aspect
@Component
public class LogAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // 通过该注解说明这是一个切面
    // execution: 指定拦截的类
    @Pointcut("execution(* top.yalexin.rblog.controller.*.*(..))")
    public void log() {
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(url, ip, classMethod, args);
        logger.info("request : {}", requestLog);
    }

    @After("log()")
    public void doAfter() {
//        logger.info("-------doAfter----------");
    }

    @AfterReturning(returning = "result", pointcut = "log()")
    public void doAfterReturn(Object result) {
        logger.info("result------>{}", result);
    }

    private class RequestLog {
        private String url;
        private String ip;
        private String className;
        private Object[] args;

        @Override
        public String toString() {
            return "{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", className='" + className + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }

        public RequestLog(String url, String ip, String className, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.className = className;
            this.args = args;
        }
    }


}
