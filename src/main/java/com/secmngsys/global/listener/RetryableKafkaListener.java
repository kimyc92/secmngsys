package com.secmngsys.global.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.protocol.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.kafka.retrytopic.RetryTopicConfiguration;
import org.springframework.kafka.retrytopic.RetryTopicConfigurationBuilder;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
import org.springframework.kafka.support.EndpointHandlerMethod;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.serializer.DeserializationException;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;

@Slf4j
@Component
public class RetryableKafkaListener {

//
//    private final KafkaTemplate<String, String> kafkaTemplate;
//
//    @Autowired
//    public RetryableKafkaListener(KafkaTemplate<String, String> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }

//    @RetryableTopic(
//            attempts = "2",
//            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE,
//            backoff = @Backoff(delay = 1000, multiplier = 2.0),
//            dltStrategy = DltStrategy.FAIL_ON_ERROR,
//            exclude = {SerializationException.class, DeserializationException.class}
//    )
//    @KafkaListener(
//            groupId = "secmngsys",
//            topics  = "quickstart-events"
//            // containerFactory 지정안할시 kafkaListenerContainerFactory 참조
//            //, containerFactory = "concurrentFactory"
//    )
//    public void handleMessage(String message
//            , @Headers MessageHeaders messageHeaders
//            , @Header(KafkaHeaders.RECEIVED_TOPIC) String topic
////            , @Header(KafkaHeaders.ORIGINAL_OFFSET) Long offset
//            , @Header(KafkaHeaders.EXCEPTION_FQCN) String descException
////            , @Header(KafkaHeaders.EXCEPTION_STACKTRACE) String stacktrace
////            , @Header(KafkaHeaders.EXCEPTION_MESSAGE) String errorMessage
//    ){
//        log.info("Received message: {} from topic: {}", message, topic);
//        System.out.println("check -->> "+messageHeaders);
//        throw new RuntimeException("Test exception");
//    }

    @DltHandler
    public void handleDlt(String message
            , @Headers MessageHeaders messageHeaders
            , @Header(KafkaHeaders.RECEIVED_TOPIC) String topic
//            , @Header(KafkaHeaders.ORIGINAL_OFFSET) Long offset
            , @Header(KafkaHeaders.EXCEPTION_FQCN) String descException
//            , @Header(KafkaHeaders.EXCEPTION_STACKTRACE) String stacktrace
//            , @Header(KafkaHeaders.EXCEPTION_MESSAGE) String errorMessage
    ) {
        log.info("Message: {} handled by dlq topic: {}", message, topic);
        System.out.println("check -->> "+messageHeaders);
        System.out.println(message);
        System.out.println(topic);
        //System.out.println(offset);
        System.out.println(descException);
        //System.out.println(stacktrace);
        //System.out.println(errorMessage);
    }

    // 우선순위 RetryTopicConfigurationBuilder dltHandlerMethod > @DltHandler
//    @Bean
//    public RetryTopicConfiguration retryTopicConfig() throws NoSuchMethodException { // KafkaTemplate<String, String> kafkaTemplate
//        return RetryTopicConfigurationBuilder
//                .newInstance()
//                .autoCreateTopicsWith(REPLICA_COUNT, KafkaConsumerConstants.REPLICATION_FACTOR)
//                .maxAttempts(KafkaConsumerConstants.MAX_ATTEMPT_COUNT)
//                .fixedBackOff(KafkaConsumerConstants.BACK_OFF_PERIOD)
//                .listenerFactory(kafkaListenerContainerFactory())
//                .setTopicSuffixingStrategy(TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE)
//                .dltHandlerMethod(new EndpointHandlerMethod(ConsumerErrorsHandler.class, "postProcessDltMessage"))
//                .create(kafkaTemplate);
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory()); // 각 서비스 별 consumer 설정값
//        factory.setMessageConverter(new JsonMessageConverter(objectMapper)); // @Payload로 객체 파싱할 때 필요
//        return factory;
//    }
}
