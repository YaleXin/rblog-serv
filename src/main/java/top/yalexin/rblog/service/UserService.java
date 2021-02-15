/**
 * Author: Yalexin
 * Email: me@yalexin.top
 **/
package top.yalexin.rblog.service;

import top.yalexin.rblog.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService {

    User getUser(HttpServletRequest request, HttpServletResponse response);

    int login(User user, String salt, HttpServletRequest request, HttpServletResponse response);

    int logout(HttpServletRequest request, HttpServletResponse response);

    Long updateByUsernameAndPsw(String newPsw, String username, String oldPsw, HttpServletRequest request);
}
