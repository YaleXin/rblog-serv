/**
 * Author: Yalexin
 * Email: me@yalexin.top
 **/
package top.yalexin.rblog.controller.admin;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.yalexin.rblog.annotation.SentinelIpLimiter;
import top.yalexin.rblog.constant.SentinelConstant;
import top.yalexin.rblog.entity.User;
import top.yalexin.rblog.service.UserService;
import top.yalexin.rblog.util.IPUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@ResponseBody
@RequestMapping("/admin")
public class AdminLoginController {
    @Autowired
    private UserService userService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @SentinelIpLimiter(
            value = SentinelConstant.ADMIN_LOGIN_RULE,
            entryType = EntryType.IN,
            batchCount = 1
    )
    @PostMapping("/login")
    public ResponseEntity login(HttpServletRequest request,
                                HttpServletResponse response,
                                @RequestBody JSONObject json) {
        return tryLogin(request, response, json);
    }

    private ResponseEntity tryLogin(HttpServletRequest request,
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
//             用户不存在 自定义的状态码：402
            return new ResponseEntity(map, HttpStatus.valueOf(402));
        } else if (code == 2) {
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


    // 返回一个 config ，指明 pow 的困难度和随机前缀
    @SentinelIpLimiter(
            value = SentinelConstant.ADMIN_POW_RULE,
            entryType = EntryType.IN,
            batchCount = 1
    )
    @GetMapping("/powConfig")
    public ResponseEntity getPowConfig(HttpServletRequest request,
                                       HttpServletResponse response) {
        Map map = userService.getPowConfig(request, response);
        return new ResponseEntity(map, HttpStatus.OK);
    }

    // 根据客户端提交的哈希值以及 padding，正确是否符合 pow ，即产生的哈希值是否满足条件
    @SentinelIpLimiter(
            value = SentinelConstant.ADMIN_POW_VERIFY_RULE,
            entryType = EntryType.IN,
            batchCount = 1
    )
    @PostMapping("/powVerify")
    public ResponseEntity powVerify(HttpServletRequest request,
                                    HttpServletResponse response, @RequestBody JSONObject json) {
        Map map = userService.verifyPow(request, response, json.getJSONObject("data"));
        return new ResponseEntity(map, HttpStatus.OK);
    }
}
