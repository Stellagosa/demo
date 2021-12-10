package com.stellagosa.demo.redisbatch.service;

import com.stellagosa.demo.redisbatch.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void batch() {
        List<User> list = new ArrayList<>();
        for(int i = 0; i < 100000; i++) {
            User user = new User();
            user.setId(i);
            user.setName("测试" + i);
            list.add(user);
        }
        long start = System.currentTimeMillis();
        userService.batch(list);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    @Test
    void getKeysTest() {
        List<String> keys = userService.keys();
        System.out.println(keys.size());
        // keys.forEach(System.out::println);
    }

    @Test
    void delete() {
        userService.delete();
    }

    @Test
    void deleteBatch() {
        long start = System.currentTimeMillis();
        userService.deleteBatch(userService.keys());
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}