package com.stellagosa.demo.webSocket.chatroom.controller;

import com.stellagosa.demo.webSocket.chatroom.config.AllUserList;
import com.stellagosa.demo.webSocket.chatroom.config.User;
import com.stellagosa.demo.webSocket.chatroom.config.UserOnlineManager;
import com.stellagosa.demo.webSocket.chatroom.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Stellagosa
 * @Date: 2022/2/8 17:26
 * @Description: 用户登录管理
 */
@RestController
public class UserLoginController {

    private final AllUserList userList;
    private final UserOnlineManager userOnlineManager;

    public UserLoginController(AllUserList userList, UserOnlineManager userOnlineManager) {
        this.userList = userList;
        this.userOnlineManager = userOnlineManager;
    }

    @PostMapping("/login")
    public Result<String> login(@RequestBody User userParam) {
        if (userParam.getUsername() == null || userParam.getPassword() == null) {
            return new Result<>(400, "用户名或密码错误！");
        }
        User user = userList.searchByUsername(userParam.getUsername());
        if (userParam.getUsername().equals(user.getUsername()) && userParam.getPassword().equals(user.getPassword())) {
            String token = userOnlineManager.addUser(user);
            return new Result<>(200, "登陆成功！", token);
        }
        return new Result<>(400, "用户名或密码错误！");
    }

    @GetMapping("/user/allOnline")
    public Result<List<String>> allOnlineUsers() {
        List<String> list = userOnlineManager.getAllUser().stream().map(User::getUsername).collect(Collectors.toList());
        return new Result<>(200, "操作成功！", list);
    }

}
