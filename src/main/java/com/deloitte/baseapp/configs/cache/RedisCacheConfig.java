package com.deloitte.baseapp.configs.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
//@EnableCaching
public class RedisCacheConfig {

    @Value("${spring.redis.host}")
    private String HOST;

    @Value("${spring.redis.port}")
    private Integer PORT;

    @Bean
    public RedisTemplate<String, String> redisTemplate() {
        final RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new GenericToStringSerializer<String>(String.class));
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());

        RedisStandaloneConfiguration configuration =
                new RedisStandaloneConfiguration(HOST, PORT);

        JedisClientConfiguration jedisClientConfiguration =
                JedisClientConfiguration.builder().build();

        JedisConnectionFactory factory =
                new JedisConnectionFactory(configuration, jedisClientConfiguration);

        factory.afterPropertiesSet();
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }
}
