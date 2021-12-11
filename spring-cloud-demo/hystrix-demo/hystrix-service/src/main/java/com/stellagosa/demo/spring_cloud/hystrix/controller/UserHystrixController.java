package com.stellagosa.demo.spring_cloud.hystrix.controller;

import cn.hutool.core.thread.ThreadUtil;
import com.stellagosa.demo.spring_cloud.hystrix.common.CommonResult;
import com.stellagosa.demo.spring_cloud.hystrix.entity.User;
import com.stellagosa.demo.spring_cloud.hystrix.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @Author: Stellagosa
 * @Date: 2021/12/10 17:11
 * @Description:
 */
@RestController
@RequestMapping("/user")
public class UserHystrixController {

    @Autowired
    private UserService userService;

    @GetMapping("/testFallback/{id}")
    public CommonResult getUser(@PathVariable("id") Integer id) {
        return userService.getUser(id);
    }

    @GetMapping("/testCommand/{id}")
    public CommonResult getUserCommond(@PathVariable("id") Integer id) {
        return userService.getUserCommand(id);
    }

    @GetMapping("/testException/{id}")
    public CommonResult getUserException(@PathVariable("id") Integer id) {
        return userService.getUserException(id);
    }

    @GetMapping("/testCache/{id}")
    public CommonResult getUserCache(@PathVariable("id") Integer id) {
        userService.getUserCache(id);
        userService.getUserCache(id);
        userService.getUserCache(id);
        return new CommonResult(200, "操作成功");
    }

    @GetMapping("/testCollapser")
    public CommonResult getUserBatch() throws ExecutionException, InterruptedException {
        Future<User> future1 = userService.GetUserFuture(1);
        Future<User> future2 = userService.GetUserFuture(2);
        future1.get();
        future2.get();
        ThreadUtil.safeSleep(200);
        Future<User> future3 = userService.GetUserFuture(3);
        future3.get();
        return new CommonResult(200, "操作成功");
    }
}
