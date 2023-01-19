package com.secmngsys.global.configuration.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Configuration;
import org.apache.camel.component.kafka.KafkaConfiguration;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class kafkaConfig {
//    @Value(value = "${kafka.host}")
//    private String bootstrapAddress;

//    @Value(value = "${spring.kafka.template.default-topic}")
//    private String topicName;

//    @Bean
//    public KafkaAdminClient kafkaAdminClient() {
//        Map<String, Object> configs = new HashMap<>();
//        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
//        return new KafkaAdminClient(configs);
//    }
//
    @Bean
    public NewTopic topic1() {
        System.out.println("asdA?SDA?SDA?SD?ASD?");

        //System.out.println("확인 - "+topicName);
        return new NewTopic("hoih", 1, (short) 1);
    }

//    @Bean
//    public KafkaConfiguration kafkaConfiguration(){
//        System.out.println("asdA?SDA?SDA?SD?ASD?");
//        KafkaConfiguration kafkaConfiguration = new KafkaConfiguration();
//        kafkaConfiguration.setRequestRequiredAcks("all");
//        return kafkaConfiguration;
//    }
}
