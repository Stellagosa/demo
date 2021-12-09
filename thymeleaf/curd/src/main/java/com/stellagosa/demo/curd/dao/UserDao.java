package com.stellagosa.demo.curd.dao;

import com.stellagosa.demo.curd.entity.User;

public interface UserDao {

    User queryByUsername(String username);

}
