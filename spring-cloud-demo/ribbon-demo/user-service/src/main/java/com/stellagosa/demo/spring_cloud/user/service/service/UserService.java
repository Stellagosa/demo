package com.stellagosa.demo.spring_cloud.user.service.service;

import com.stellagosa.demo.spring_cloud.user.service.entity.User;

import java.util.List;

/**
 * @Author: Stellagosa
 * @Date: 2021/12/10 16:32
 * @Description:
 */
public interface UserService {

    void create(User user);

    User getUser(Integer id);

    List<User> getUserByIds(List<Integer> ids);

    User getByUsername(String username);

    void update(User user);

    void delete(Integer id);
}
