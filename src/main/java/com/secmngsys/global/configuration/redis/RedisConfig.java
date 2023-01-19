package com.secmngsys.global.configuration.redis;

import com.secmngsys.global.configuration.database.DataSourceConfig;
import com.secmngsys.global.util.AES256Util;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;

@Configuration
public class RedisConfig {

    private static String key = "my_key";

    @Resource
    private Environment env;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() throws Exception {
        /*
        String redisHost = env.getProperty("prd.redis.lettuce.host");
        String redisPort = env.getProperty("prd.redis.lettuce.port");
        String redisPassword = env.getProperty("prd.redis.lettuce.password");
        System.out.println("redis redisHost - "+redisHost);
        System.out.println("redis redisPort - "+redisPort);
        System.out.println("redis redisPassword - "+AES256Util.decryptAES256(redisPassword, key));
         */
       // System.out.println("?????");
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(env.getProperty("prd.redis.lettuce.host"));
        redisStandaloneConfiguration.setPort(Integer.parseInt(env.getProperty("prd.redis.lettuce.port")));
        redisStandaloneConfiguration.setPassword(AES256Util.decryptAES256(env.getProperty("prd.redis.lettuce.password"), key));

        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);
        return lettuceConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() throws Exception {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }

}
