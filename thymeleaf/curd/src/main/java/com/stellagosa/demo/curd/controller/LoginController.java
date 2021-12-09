package com.stellagosa.demo.curd.controller;

import com.stellagosa.demo.curd.entity.User;
import com.stellagosa.demo.curd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private UserService userService;

    // 登录请求
    @PostMapping("/login")
    public String login(User user, Map<String, Object> map, HttpSession session) {
        if (userService.loginJudge(user)) {
            //登录成功 防止表单重复提交，重定向到主页,需要拦截器进行登录检查，不然都可以登录这个页面了
            session.setAttribute("loginUser", user.getUsername());
            return "redirect:/main.html";
        } else {
            // 登陆失败
            map.put("msg", "用户名或密码填写错误！");
            return "/login";
        }
    }

    // 退出请求
    @GetMapping("/quit")
    public String quit(Map<String, Object> map, HttpSession session) {
        session.setAttribute("loginUser", null);
        map.put("msg", "请重新登陆！");
        return "/login";
    }
}
