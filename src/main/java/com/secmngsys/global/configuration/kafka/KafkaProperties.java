package com.secmngsys.global.configuration.kafka;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
@Validated
@ConfigurationProperties(prefix = "spring.kafka")
@ConstructorBinding
@ConditionalOnProperty(value = "mode.kafka", havingValue = "true", matchIfMissing = false)
public class KafkaProperties {

//    @Value("${kafka.consumer.group}")
//    private String consumerGroupId;
    //@Value("${spring.kafka.template.default-topic}")
    //private String templateDefaultTopic;
    @NonNull @NotEmpty private final String bootstrapServers;

    @Pattern(regexp = "^file:.*/$")
    @NonNull @NotEmpty private final String endpointConsumerOffsetLogDir;

    @Pattern(regexp = "^file:.*/$")
    @NonNull @NotEmpty private final String endpointProducerOffsetLogDir;

    @Pattern(regexp = "^[0-7][0-7]0$")
    @NonNull @NotEmpty private final String endpointLogFilePermission;

    @Pattern(regexp = "^kafka:$")
    @NonNull @NotEmpty private final String endpointPrefix;
    //@NonNull @NotEmpty private final String seekTo;
    @NonNull @NotEmpty private final Map<String, Object> consumer;
    @NonNull @NotEmpty private final Map<String, Object> producer;
    @NonNull @NotEmpty private final Map<String, Object> template;

}
