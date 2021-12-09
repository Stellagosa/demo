package com.stellagosa.demo.cache.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stellagosa.demo.cache.entity.User;
import com.stellagosa.demo.cache.mapper.UserMapper;
import com.stellagosa.demo.cache.service.IUserService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Stellagosa
 * @Date: 2021/12/9 15:01
 * @Description: User 服务实现类
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @CacheEvict(value = "user", allEntries = true)
    @Override
    public boolean addUser(User user) {
        return this.save(user);
    }

    @CacheEvict(value = "user", allEntries = true)
    @Override
    public boolean deleteUser(String id) {
        return this.removeById(id);
    }

    @CacheEvict(value = "user", allEntries = true)
    @Override
    public boolean updateUser(User user) {
        return this.updateById(user);
    }

    @Cacheable(value = "user", keyGenerator = "myKeyGenerator", unless = "#result == null || #result.size() == 0")
    @Override
    public List<User> listAll(String condition) {
        System.out.println("执行查询了！！！");
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (condition != null && !condition.trim().isEmpty()) {
            wrapper.likeRight("username", condition);
        }
        return this.list(wrapper);
    }

}
