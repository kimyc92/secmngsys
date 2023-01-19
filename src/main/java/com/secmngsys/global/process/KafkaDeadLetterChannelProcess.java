package com.secmngsys.global.process;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaDeadLetterChannelProcess implements Processor {

    @Override
    public void process(Exchange exchange) {
        log.error("KafkaDeadLetterChannelProcess()");
        System.out.println("getHeaders() ->> "+exchange.getIn().getHeaders());
        System.out.println("getBody() ->> "+exchange.getIn().getBody());
        String body = (String) exchange.getIn().getBody();
        exchange.getIn().setBody(body+"내맘이야");
    }
}
