package com.stellagosa.demo.spring_cloud.user.service.service.impl;

import com.stellagosa.demo.spring_cloud.user.service.entity.User;
import com.stellagosa.demo.spring_cloud.user.service.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Stellagosa
 * @Date: 2021/12/10 16:32
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {

    private List<User> userList;

    @Override
    public void create(User user) {
        userList.add(user);
    }

    @Override
    public User getUser(Integer id) {
        List<User> collect = userList.stream().filter(user -> user.getId().equals(id)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(collect)) {
            return collect.get(0);
        }
        return null;
    }

    @Override
    public List<User> getUserByIds(List<Integer> ids) {
        return userList.stream().filter(user -> ids.contains(user.getId())).collect(Collectors.toList());
    }

    @Override
    public User getByUsername(String username) {
        List<User> collect = userList.stream().filter(user -> user.getUsername().equals(username))
                                     .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(collect)) {
            return collect.get(0);
        }
        return null;
    }

    @Override
    public void update(User user) {
        userList.stream().filter(userItem -> userItem.getId().equals(user.getId())).forEach(userItem -> {
            userItem.setUsername(user.getUsername());
            userItem.setPassword(user.getPassword());
        });
    }

    @Override
    public void delete(Integer id) {
        User user = getUser(id);
        if (user != null) {
            userList.remove(user);
        }
    }

    @PostConstruct
    public void init() {
        userList = new ArrayList<>();
        userList.add(new User(1, "张三", "123456"));
        userList.add(new User(2, "李四", "123456"));
        userList.add(new User(3, "王五", "123456"));
        userList.add(new User(4, "关羽", "123456"));
    }

}
