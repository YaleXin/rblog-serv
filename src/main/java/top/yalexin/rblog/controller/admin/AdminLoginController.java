/**
 * Author: Yalexin
 * Email: me@yalexin.top
 **/
package top.yalexin.rblog.controller.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.yalexin.rblog.entity.Blog;
import top.yalexin.rblog.entity.User;
import top.yalexin.rblog.service.UserService;
import top.yalexin.rblog.util.MD5Utils;
import top.yalexin.rblog.util.RandomValidateCodeUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@ResponseBody
@RequestMapping("/admin")
public class AdminLoginController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity login(HttpServletRequest request,
                                HttpServletResponse response,
                                @RequestBody JSONObject json) {
        HashMap data = (HashMap) json.get("data");
        HashMap userMap = (HashMap) data.get("user");
        String codeStr = (String) data.get("code");

        User user = new User();
        user.setUsername((String) userMap.get("username"));
        user.setPassword((String) userMap.get("password"));
        String salt = (String) data.get("salt");
        int code = userService.login(user, salt, codeStr, request, response);
        HashMap<String, User> map = new HashMap<>();
        if (code == 0) {
            User sessionUser = (User) request.getSession().getAttribute("user");
            map.put("user", sessionUser);
            return new ResponseEntity(map, HttpStatus.OK);
        } else if (code == 1) {
//             用户不存在
            return new ResponseEntity(map, HttpStatus.valueOf(402));
        } else if(code == 2){
//             密码错误
            return new ResponseEntity(map, HttpStatus.valueOf(403));
        } else {
//            验证码错误
            return new ResponseEntity(map, HttpStatus.valueOf(405));
        }
    }

    @DeleteMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request,
                                 HttpServletResponse response) {
        int logout = userService.logout(request, response);
        return new ResponseEntity(logout, HttpStatus.OK);
    }

    @GetMapping("/verifyCode")
    public ResponseEntity verifyCode(HttpServletRequest request,
                                 HttpServletResponse response) {
        Map map = userService.verifyCode(request, response);

        return new ResponseEntity(map, HttpStatus.OK);
    }
}
