/**
 * Author: Yalexin
 * Email: me@yalexin.top
 **/
package top.yalexin.rblog.controller.admin;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.yalexin.rblog.entity.User;
import top.yalexin.rblog.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Controller
@ResponseBody
@RequestMapping("/admin/user")
public class AdminUserController {
    @Autowired
    UserService userService;

    @GetMapping("/info")
    public ResponseEntity getUserInfo(HttpServletRequest request, HttpServletResponse response) {
        User user = userService.getUser(request, response);
        HashMap<String, User> map = new HashMap<>();
        map.put("user", user);
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity updateUserInfo(HttpServletRequest request,
                                         HttpServletResponse response,
                                         @RequestBody JSONObject json) {
        HashMap data = (HashMap) json.get("data");
        String oldPsw = (String) data.get("oldPsw");
        String newPsw = (String) data.get("newPsw");
        String username = (String) data.get("username");
        Long updateResult = userService.updateByUsernameAndPsw(newPsw, username, oldPsw, request);
        return new ResponseEntity(updateResult, HttpStatus.OK);
    }
}
