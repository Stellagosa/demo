package com.stellagosa.demo.redisbatch.service;

import cn.hutool.json.JSONUtil;
import com.stellagosa.demo.redisbatch.entity.User;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private static final String REDIS_KEY = "REDIS_BATCH_TEST::";
    private final StringRedisTemplate stringRedisTemplate;

    public UserService(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 批量插入
     * @param list
     */
    public void batch(List<User> list) {
//        System.out.println(stringRedisTemplate);
        stringRedisTemplate.executePipelined((RedisCallback<String>) redisConnection -> {
            StringRedisConnection connection = (StringRedisConnection) redisConnection;
            for(User user : list) {
                String key = REDIS_KEY + user.getId();
                String value = JSONUtil.toJsonStr(user);
                connection.set(key.getBytes(), value.getBytes());
            }
            return null;
        });
    }

    /**
     * scan获取 key
     * @return
     */
    public List<String> keys() {
        List<String> list = new ArrayList<>();
        RedisConnectionFactory connectionFactory = stringRedisTemplate.getConnectionFactory();
        assert connectionFactory != null;
        RedisConnection conn = connectionFactory.getConnection();
        Cursor<byte[]> cursor = conn.scan(ScanOptions.scanOptions().match(REDIS_KEY + "*").build());
        while(cursor.hasNext()) {
            list.add(new String(cursor.next()));
        }
        return list;
    }

    /**
     * 批量删除
     * @param list
     */
    public void deleteBatch(List<String> list) {
        stringRedisTemplate.executePipelined(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                StringRedisConnection conn = (StringRedisConnection) connection;
                for(String s : list) {
                    conn.del(s);
                }
                return null;
            }
        });
    }

    public Set<String> getKeys() {
        return stringRedisTemplate.keys(REDIS_KEY + "*");
    }

    public void delete() {
        Set<String> keys = getKeys();
        for(String key : keys) {
            stringRedisTemplate.delete(key);
        }

    }
    public void add(List<User> list) {
        for(User user : list) {
            stringRedisTemplate.opsForValue().set(REDIS_KEY +user.getId(), JSONUtil.toJsonStr(user));
        }
    }

}
