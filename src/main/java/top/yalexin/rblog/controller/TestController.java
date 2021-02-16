/**
 * Author: Yalexin
 * Email: me@yalexin.top
 **/
package top.yalexin.rblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller

@RequestMapping("test")
public class TestController {

    @RequestMapping("/toSelfEmailTemplate")
    private String sayHello() {
        return "toSelfEmailTemplate1";
    }

    @RequestMapping("/toSelfEmailTemplate1")
    private String sayHello1() {
        return "toSelfEmailTemplate1.html";
    }


    @RequestMapping("/emailTemplate")
    private String emailTemplate() {
        return "emailTemplate1";
    }

    @RequestMapping("/emailTemplate1")
    private String emailTemplate1() {
        return "emailTemplate1.html";
    }
    @ResponseBody
    @RequestMapping("/index")
    public ModelAndView index(ModelAndView mv) {
        mv.setViewName("index");
        return mv;
    }

    @ResponseBody
    @RequestMapping("/index1")
    public ModelAndView index1(ModelAndView mv) {
        mv.setViewName("/index");
        return mv;
    }


    @RequestMapping("/index2")
    public ModelAndView index2() {
        ModelAndView mv =  new ModelAndView("index");
        return mv;
    }



}
