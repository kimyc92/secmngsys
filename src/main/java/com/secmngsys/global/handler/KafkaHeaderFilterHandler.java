package com.secmngsys.global.handler;

import com.secmngsys.domain.certification.service.CertificationService;
import com.secmngsys.domain.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Configuration;
import org.apache.camel.Exchange;
import org.apache.camel.component.kafka.KafkaHeaderFilterStrategy;
import org.apache.camel.spi.HeaderFilterStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
public class KafkaHeaderFilterHandler implements HeaderFilterStrategy {

    @Override
    public boolean applyFilterToCamelHeaders(String headerName
            , Object headerValue, Exchange exchange) {
        log.debug("applyFilterToCamelHeaders()");
        return true;
    }

    @Override
    public boolean applyFilterToExternalHeaders(String headerName
            , Object headerValue, Exchange exchange) {
        log.debug("applyFilterToExternalHeaders()");
        KafkaHeaderDeserializerHandler kafkaHeaderDeserializerHandler =
                new KafkaHeaderDeserializerHandler();
        System.out.println(" ");
        System.out.println("<<<<<applyFilterToExternalHeaders>>>>>>");
        System.out.println("------------------------------------");
        System.out.println("headerName  -> "+headerName);
        System.out.println("headerValue -> "+kafkaHeaderDeserializerHandler.deserialize(headerName, (byte[]) headerValue));
       // System.out.println(kafkaHeaderDeserializerHandler.deserialize(headerName, (byte[]) headerValue));
        return true;
    }
}
