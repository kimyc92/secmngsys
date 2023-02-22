package com.secmngsys.global.configuration.camel;

import com.secmngsys.global.handler.CamelMaskingFormatterHandler;
import com.secmngsys.global.handler.KafkaHeaderDeserializerHandler;
import com.secmngsys.global.handler.KafkaHeaderFilterHandler;
import com.secmngsys.global.listener.CamelLogListener;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ExtendedCamelContext;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.DeadLetterChannelBuilder;
import org.apache.camel.builder.DefaultErrorHandlerBuilder;
import org.apache.camel.component.kafka.serde.KafkaHeaderDeserializer;
import org.apache.camel.processor.errorhandler.RedeliveryPolicy;
import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.apache.camel.support.SimpleRegistry;
import org.apache.camel.spi.MaskingFormatter;
import org.apache.camel.spi.Registry;
//import org.apache.camel.support.processor.DefaultMaskingFormatter;
import org.apache.camel.support.processor.DefaultMaskingFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.apache.camel.model.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Servlet;
import java.util.*;

@Configuration
public class CamelConfig {

    private CamelContext camelContext;

    public CamelConfig(CamelContext camelContext) {
        this.camelContext = camelContext;
    }

    @Bean
    public void ExtendedCamelContext() {
        //camelContext.getGlobalOptions().put(Exchange.LOG_EIP_NAME, "com.foo.myapp");
        //camelContext.getGlobalOptions().put(Exchange.LOG_DEBUG_BODY_STREAMS, "true"); // stream log enabled
        ExtendedCamelContext ecc = (ExtendedCamelContext) camelContext;
        ecc.addLogListener(new CamelLogListener());
    }

    @Bean
    public void MaskingRegistry() {
        Set SECMNGSYS_MASKING_KEYWORDS = new HashSet(Arrays.asList("HpNo", "password", "pw"));
        CamelMaskingFormatterHandler formatter =
                new CamelMaskingFormatterHandler(SECMNGSYS_MASKING_KEYWORDS
                        , true
                        ,true
                        ,true
                );
        camelContext.getRegistry().bind(MaskingFormatter.CUSTOM_LOG_MASK_REF, formatter);
    }

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
