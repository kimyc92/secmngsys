package com.secmngsys.global.configuration.redis;

import com.secmngsys.global.configuration.database.DataSourceConfig;
import com.secmngsys.global.configuration.kafka.KafkaProperties;
import com.secmngsys.global.util.AES256Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;

@Slf4j
@Configuration
@ConditionalOnProperty(value = "mode.redis", havingValue = "true", matchIfMissing = false)
public class RedisConfig {

    private static String key = "my_key";
    protected RedisProperties redisProperties;

    public RedisConfig(RedisProperties redisProperties) {
        this.redisProperties = redisProperties;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() throws Exception {
        log.debug("redisConnectionFactory()");
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisProperties.getHost());
        redisStandaloneConfiguration.setPort(Integer.parseInt(redisProperties.getPort()));
        redisStandaloneConfiguration.setPassword(AES256Util.decryptAES256(redisProperties.getPassword(), key));
        //redisStandaloneConfiguration.setHostName(env.getProperty("prd.redis.lettuce.host"));
        //redisStandaloneConfiguration.setPort(Integer.parseInt(env.getProperty("prd.redis.lettuce.port")));
        //redisStandaloneConfiguration.setPassword(AES256Util.decryptAES256(env.getProperty("prd.redis.lettuce.password"), key));
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
