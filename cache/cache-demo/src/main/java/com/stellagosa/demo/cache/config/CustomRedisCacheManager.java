package com.stellagosa.demo.cache.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

/**
 * @Author: Stellagosa
 * @Date: 2021/12/9 14:45
 * @Description: 自定义 RedisCacheManager，采用 GenericJackson2JsonRedisSerializer 序列化
 */
@Configuration
public class CustomRedisCacheManager {
    @Bean
    public RedisCacheManager redisCacheConfiguration(RedisConnectionFactory factory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new GenericJackson2JsonRedisSerializer()));

        return RedisCacheManager.builder(factory).cacheDefaults(config).build();
    }
}
