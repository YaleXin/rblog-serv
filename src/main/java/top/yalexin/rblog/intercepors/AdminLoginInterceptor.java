/**
 * Author: Yalexin
 * Email: me@yalexin.top
 **/
package top.yalexin.rblog.intercepors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import top.yalexin.rblog.entity.User;
import top.yalexin.rblog.util.IPUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class AdminLoginInterceptor implements HandlerInterceptor {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String iRealIPAddr = IPUtils.getIRealIPAddr(request);

        User user = (User) session.getAttribute("user");
        if (user == null) {

            logger.info("access admin, must login first! IP ---> {}  ", iRealIPAddr);
            logger.debug("access admin, must login first! IP ---> {}  ", iRealIPAddr);

            // 约定为 未登录状态
            response.setStatus(480);
            return false;
        } else {

            StringBuffer requestURL = request.getRequestURL();
            logger.info("access admin, already login, IP ---> {}  ,requestURL ---> {}", iRealIPAddr, requestURL);
            logger.debug("access admin, already login, IP ---> {}  ,requestURL ---> {}", iRealIPAddr, requestURL);

            return true;
        }
    }
}
