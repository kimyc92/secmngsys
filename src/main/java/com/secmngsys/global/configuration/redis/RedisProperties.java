package com.secmngsys.global.configuration.redis;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.Map;

@Getter
@RequiredArgsConstructor
@Validated
@ConfigurationProperties(prefix = "prd.redis.lettuce")
@ConstructorBinding
@ConditionalOnProperty(value = "mode.redis", havingValue = "true", matchIfMissing = false)
public class RedisProperties {

    @NonNull @NotEmpty private final Map<String, Object> pool;
    @NonNull @NotEmpty private final String port;
    @NonNull @NotEmpty private final String host;
    @NonNull @NotEmpty private final String password;

}
