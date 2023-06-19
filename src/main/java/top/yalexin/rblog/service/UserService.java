/**
 * Author: Yalexin
 * Email: me@yalexin.top
 **/
package top.yalexin.rblog.service;

import com.alibaba.fastjson.JSONObject;
import top.yalexin.rblog.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface UserService {

    User getUser(HttpServletRequest request, HttpServletResponse response);

    int login(User user, String salt, String codeStr,HttpServletRequest request, HttpServletResponse response);

    int logout(HttpServletRequest request, HttpServletResponse response);
    Map verifyCode(HttpServletRequest request, HttpServletResponse response);

    Map verifyPow(HttpServletRequest request, HttpServletResponse response, JSONObject json);

    Map getPowConfig(HttpServletRequest request, HttpServletResponse response);

    Long updateByUsernameAndPsw(String newPsw, String username, String oldPsw, HttpServletRequest request);
}
