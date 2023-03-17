package top.yalexin.rblog.intercepors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import top.yalexin.rblog.util.IPUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminTryLoginLogInterceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String iRealIPAddr = IPUtils.getIRealIPAddr(request);
        logger.info("try admin login, IP ---> {}  ", iRealIPAddr);
        logger.debug("try admin login, IP ---> {}  ", iRealIPAddr);
        return true;
    }
}
