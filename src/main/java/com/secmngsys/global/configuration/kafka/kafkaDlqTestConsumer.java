package com.secmngsys.global.configuration.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ConditionalOnProperty(value = "mode.kafka", havingValue = "true", matchIfMissing = false)
public class kafkaDlqTestConsumer {

//    @Bean
//    public SeekToCurrentErrorHandler errorHandler(DeadLetterPublishingRecoverer deadLetterPublishingRecoverer) {
//        return new SeekToCurrentErrorHandler(deadLetterPublishingRecoverer);
//    }
//
//    @Bean
//    public DeadLetterPublishingRecoverer publisher(KafkaOperations<String, Object> operations) {
//        return new DeadLetterPublishingRecoverer(operations);
//    }
//
//    @KafkaListener(id = "test-dlq", topics = "custom-message-topic")
//    public void listen(CustomMessage value) {
//        log.info("listen {}", value);
//    }

}
