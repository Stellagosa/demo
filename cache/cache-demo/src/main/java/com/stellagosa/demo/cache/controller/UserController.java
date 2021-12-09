package com.stellagosa.demo.cache.controller;

import com.stellagosa.demo.cache.entity.User;
import com.stellagosa.demo.cache.result.CommonResult;
import com.stellagosa.demo.cache.result.ResultTool;
import com.stellagosa.demo.cache.service.IUserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Stellagosa
 * @Date: 2021/12/9 15:03
 * @Description: User 前端控制器
 */
@RestController
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public CommonResult add(@RequestBody User user) {
        return userService.addUser(user) ? ResultTool.success() : ResultTool.fail();
    }

    @DeleteMapping("user/{id}")
    public CommonResult delete(@PathVariable String id) {
        return userService.deleteUser(id) ? ResultTool.success() : ResultTool.fail();
    }

    @PutMapping("/user")
    public CommonResult update(@RequestBody User user) {
        return userService.updateUser(user) ? ResultTool.success() : ResultTool.fail();
    }

    @GetMapping("/user/all")
    public CommonResult<List<User>> listAll(String condition) {
        return ResultTool.success(userService.listAll(condition));
    }

}
