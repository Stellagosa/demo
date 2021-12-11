package com.stellagosa.demo.spring_cloud.hystrix.service;

import com.stellagosa.demo.spring_cloud.hystrix.common.CommonResult;
import com.stellagosa.demo.spring_cloud.hystrix.entity.User;

import java.util.concurrent.Future;

/**
 * @Author: Stellagosa
 * @Date: 2021/12/10 17:11
 * @Description:
 */
public interface UserService {

    CommonResult getUser(Integer id);

    CommonResult getUserCommand(Integer id);

    CommonResult getUserException(Integer id);

    CommonResult getUserCache(Integer id);

    Future<User> GetUserFuture(Integer id);
}
