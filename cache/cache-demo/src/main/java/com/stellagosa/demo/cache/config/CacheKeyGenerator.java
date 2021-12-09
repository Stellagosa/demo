package com.stellagosa.demo.cache.config;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @Author: Stellagosa
 * @Date: 2021/12/9 14:47
 * @Description: 自定义 Redis 缓存主键生成策略
 */
@Component
public class CacheKeyGenerator {
    @Bean
    public KeyGenerator myKeyGenerator() {
        return (target, method, params) -> {
            String pre = target.getClass().getSimpleName() + "-" + method.getName();
            String args = StringUtils.arrayToDelimitedString(params, "-");
            return args.isEmpty() ? pre : pre + "-" + args;
        };
    }
}
