package com.secmngsys.global.configuration.camel;

import com.secmngsys.global.handler.KafkaHeaderDeserializerHandler;
import com.secmngsys.global.handler.KafkaHeaderFilterHandler;
import org.apache.camel.CamelContext;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.DeadLetterChannelBuilder;
import org.apache.camel.builder.DefaultErrorHandlerBuilder;
import org.apache.camel.component.kafka.serde.KafkaHeaderDeserializer;
import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.apache.camel.processor.errorhandler.RedeliveryPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Servlet;

@Configuration
public class CamelConfig {

    @Bean
    public KafkaHeaderDeserializer kafkaHeaderDeserializerCustom(){
        return new KafkaHeaderDeserializerHandler();
    }

    @Bean
    public KafkaHeaderFilterHandler KafkaHeaderFilterStrategyCustom(){
        return new KafkaHeaderFilterHandler();
    }
//    @Bean
//    public DeadLetterChannelBuilder myErrorHandler() {
//        DeadLetterChannelBuilder deadLetterChannelBuilder = new DeadLetterChannelBuilder();
//        deadLetterChannelBuilder.setDeadLetterUri("direct:error223");
//        //deadLetterChannelBuilder.setRedeliveryPolicy(new RedeliveryPolicy());
//        deadLetterChannelBuilder.useOriginalMessage();
//        return deadLetterChannelBuilder;
//    }

//    @Autowired
//    CamelContext camelContext;
//
//    @Bean
//    public
//
//    @Bean
//    public DefaultErrorHandlerBuilder myDefaultErrorHandler() {
//        System.out.println("여기오는거임?");
//        DefaultErrorHandlerBuilder defaultErrorHandlerBuilder = new DefaultErrorHandlerBuilder();
//        defaultErrorHandlerBuilder.maximumRedeliveries(3)
//                .maximumRedeliveryDelay(3000)
//                .retryAttemptedLogLevel(LoggingLevel.WARN);
//        //camelContext.setE
//        //camelContext.setErrorHandlerBuilder(defaultErrorHandlerBuilder);
//
//        return defaultErrorHandlerBuilder;
//    }

//    @Bean
//    public DeadLetterChannelBuilder myErrorHandler() {
//        DeadLetterChannelBuilder deadLetterChannelBuilder = new DeadLetterChannelBuilder();
//        deadLetterChannelBuilder.setDeadLetterUri("direct:error");
//        deadLetterChannelBuilder.setRedeliveryPolicy(new RedeliveryPolicy().disableRedelivery());
//        deadLetterChannelBuilder.useOriginalMessage();
//        return deadLetterChannelBuilder;
//    }
}
