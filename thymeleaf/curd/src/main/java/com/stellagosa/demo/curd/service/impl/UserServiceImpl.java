package com.stellagosa.demo.curd.service.impl;

import com.stellagosa.demo.curd.dao.UserDao;
import com.stellagosa.demo.curd.entity.User;
import com.stellagosa.demo.curd.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Boolean loginJudge(User user) {
        // 忽略大小写查询的
        User queryUser = userDao.queryByUsername(user.getUsername());
        // 如果查询不出，则直接返回
        if (queryUser == null) return false;
        // 用户名字忽略大小写比较，而密码不是
        if (queryUser.getUsername().equalsIgnoreCase(user.getUsername()) &&
                queryUser.getPassword().equals(user.getPassword())) {
            return true;
        }
        return false;
    }
}
