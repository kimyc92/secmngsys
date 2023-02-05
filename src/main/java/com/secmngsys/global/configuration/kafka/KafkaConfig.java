package com.secmngsys.global.configuration.kafka;

import com.secmngsys.global.configuration.code.KafkaLogFileTypeCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Configuration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component("kafkaConfig")
@ConditionalOnProperty(value = "mode.kafka", havingValue = "true", matchIfMissing = false)
public class KafkaConfig {

    protected final KafkaProperties kafkaProperties;

    public KafkaConfig(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

//    @Bean
//    public KafkaAdminClient kafkaAdminClient() {
//        Map<String, Object> configs = new HashMap<>();
//        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
//        return new KafkaAdminClient(configs);
//    }

//    @Bean
//    public NewTopic topic1() {
//        System.out.println("asdA?SDA?SDA?SD?ASD?");
//        //System.out.println("확인 - "+topicName);
//        return new NewTopic("hoih", 1, (short) 1);
//    }

    protected StringBuilder KafkaDefaultOption(){
        StringBuilder sb = new StringBuilder();
        Map<String, Object> consumer = kafkaProperties.getConsumer();

        sb.append("?brokers=").append(kafkaProperties.getBootstrapServers())
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
        ;
        return sb;
    }

    @Bean
    public String buildKafkaDefaultUrl() {  // quickstart-events
        StringBuilder sb = new StringBuilder();
        sb.append(kafkaProperties.getEndpointPrefix())
                .append(kafkaProperties.getTemplate().get("default-topic"));
        if ("kafka:".equalsIgnoreCase(kafkaProperties.getEndpointPrefix()))
            sb.append(KafkaDefaultOption());

        return sb.toString();
    }

    @Bean
    public String buildKafkaSmsUrl() {  // sms-events
        StringBuilder sb = new StringBuilder();
        sb.append(kafkaProperties.getEndpointPrefix())
                .append(kafkaProperties.getTemplate().get("sms-topic"));
        if ("kafka:".equalsIgnoreCase(kafkaProperties.getEndpointPrefix()))
            sb.append(KafkaDefaultOption());
        return sb.toString();
    }

}
