package com.secmngsys.global.configuration.kafka;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
@Validated
@ConfigurationProperties(prefix = "spring.kafka")
@ConstructorBinding
public class KafkaProperties {

//    @Value("${kafka.consumer.group}")
//    private String consumerGroupId;
    //@Value("${spring.kafka.template.default-topic}")
    //private String templateDefaultTopic;
    @NonNull @NotEmpty private final String bootstrapServers;
    @NonNull @NotEmpty private final String endpointOffset;
    @NonNull @NotEmpty private final String endpointPrefix;
    //@NonNull @NotEmpty private final String seekTo;
    @NonNull @NotEmpty private final Map<String, Object> consumer;
    @NonNull @NotEmpty private final Map<String, Object> producer;
    @NonNull @NotEmpty private final Map<String, Object> template;

    protected StringBuilder KafkaDefaultOption(){
        StringBuilder sb = new StringBuilder();
        sb.append("?brokers=").append(bootstrapServers)
                .append("&groupId=").append(consumer.get("group-id"))
                .append("&maxPollRecords=").append(consumer.get("max-poll-records"))
                .append("&autoOffsetReset=").append(consumer.get("auto-offset-reset"))
                .append("&autoCommitEnable=").append(consumer.get("enable-auto-commit"))
                .append("&allowManualCommit=").append(consumer.get("allow-manual-commit"))
                .append("&breakOnFirstError=").append(consumer.get("break-on-firstError"))
                //.append("&headerDeserializer=").append("#kafkaHeaderDeserializerCustom")
                .append("&headerFilterStrategy=").append("#KafkaHeaderFilterStrategyCustom")
                .append("&headerDeserializer=").append("#kafkaHeaderDeserializerCustom")
                //.append("&kafkaHeaderDeserializer=").append("#kafkaHeaderDeserializerCustom")
                .append("&consumersCount=").append(1)
        //KafkaHeaderFilterStrategy
        ;
        return sb;
    }

    @Bean
    public String buildKafkaDefaultUrl() {
        StringBuilder sb = new StringBuilder();
        sb.append(endpointPrefix).append(template.get("default-topic")); // quickstart-events
        if ("kafka:".equalsIgnoreCase(endpointPrefix))
            sb.append(KafkaDefaultOption());

        return sb.toString();
    }
    @Bean
    public String buildKafkaSmsUrl() {
        StringBuilder sb = new StringBuilder();
        sb.append(endpointPrefix).append(template.get("sms-topic")); // sms-events
        if ("kafka:".equalsIgnoreCase(endpointPrefix))
            sb.append(KafkaDefaultOption());
        return sb.toString();
    }

}
