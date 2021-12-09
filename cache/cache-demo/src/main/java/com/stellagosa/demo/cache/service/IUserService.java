package com.stellagosa.demo.cache.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stellagosa.demo.cache.entity.User;

import java.util.List;

/**
 * @Author: Stellagosa
 * @Date: 2021/12/9 15:00
 * @Description: User 服务类
 */
public interface IUserService extends IService<User> {

    boolean addUser(User user);

    boolean deleteUser(String id);

    boolean updateUser(User user);

    List<User> listAll(String condition);

}
