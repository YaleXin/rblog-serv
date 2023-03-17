/**
 * Author: Yalexin
 * Email: me@yalexin.top
 **/
package top.yalexin.rblog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.yalexin.rblog.entity.Comment;
import top.yalexin.rblog.entity.User;
import top.yalexin.rblog.mapper.UserMapper;
import top.yalexin.rblog.util.IPUtils;
import top.yalexin.rblog.util.MD5Utils;
import top.yalexin.rblog.util.RandomValidateCodeUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Transactional
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    SendEmailService sendEmailService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public User getUser(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        return user;
    }

    /**
     * @param user     前端传过来的user
     * @param salt     随机密令
     * @param request
     * @param response
     * @return 0: 登录成功，1：用户不存在，2：密码错误
     */
    @Override
    public int login(User user, String salt, String codeStr, HttpServletRequest request, HttpServletResponse response) {
        final int LOGIN_SUCCESS = 0, USER_NOT_EXIST = 1, PSW_ERROR = 2, CODE_ERROR = 3;
        if (user == null || user.getUsername() == null || "".equals(user.getUsername().trim())) {
            return USER_NOT_EXIST;
        }
        String md5code = (String) request.getSession().getAttribute("code");
        if (!(MD5Utils.code("" + md5code)).equals(codeStr)){
            return CODE_ERROR;
        }
        User databaseUser = userMapper.findUser(user.getUsername());
        if (databaseUser == null) return USER_NOT_EXIST;
        // 前端传过来的密码是 md5(md5(md5(psw)) + salt)
        // 数据库中的是 md5(md5(psw))
        String saltCode = MD5Utils.code(databaseUser.getPassword() + salt);
        if (user.getPassword().equals(saltCode)) {
            // 将密码设为空 防止前端拿到密码
            databaseUser.setPassword("");
            request.getSession().setAttribute("user", databaseUser);
            Comment comment = new Comment();
            comment.setBlogId((long) 0);
            comment.setNickname("登录者");
            comment.setIp(IPUtils.getIRealIPAddr(request));
            comment.setContent("有可疑分子登录了您的博客后台，如果不是您本人操作，请做出相应处理" + "IP 地址为： " + comment.getIp());
            Comment parentCmt = new Comment();
            parentCmt.setId((long) -1);
            sendEmailService.send(parentCmt, comment, false);

            String iRealIPAddr = IPUtils.getIRealIPAddr(request);
            logger.info("admin login success!, IP ---> {}  ", iRealIPAddr);

            return LOGIN_SUCCESS;
        } else {
            return PSW_ERROR;
        }

    }

    @Override
    public int logout(HttpServletRequest request,
                      HttpServletResponse response) {
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        if (user == null) {
            return 0;
        } else {
            session.removeAttribute("user");
            return 1;
        }
    }

    @Override
    public Map verifyCode(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, String> codeMap = RandomValidateCodeUtil.getRandomValidateCode(5);

        HashMap<String, String> map = new HashMap<>();
        map.put("img", codeMap.get("base64img"));
//        先将 验证码进行 md5 加密
        String md5code = MD5Utils.code(codeMap.get("code").toLowerCase());
        request.getSession().setAttribute("code", md5code);
        map.put("code", md5code);
        return map;
    }

    @Override
    public Long updateByUsernameAndPsw(String newPsw, String username, String oldPsw, HttpServletRequest request) {
        if (newPsw == null || username == null || oldPsw == null ||
                "".equals(newPsw.trim()) || "".equals(username.trim()) || "".equals(oldPsw.trim())) {
            return Long.valueOf(0);
        }
        String md5newPsw = MD5Utils.code(newPsw);
        String md5old = MD5Utils.code(oldPsw);
        Long updateResult = userMapper.updateUser(md5newPsw, username, md5old);
        if (updateResult == null) return Long.valueOf(0);
        else {
            request.getSession().removeAttribute("user");
            return updateResult;
        }
    }
}
