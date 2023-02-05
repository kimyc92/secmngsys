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

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
public class KafkaHeaderFilterHandler implements HeaderFilterStrategy {

    @Override
    public boolean applyFilterToCamelHeaders(String headerName
            , Object headerValue, Exchange exchange) {
        log.debug("applyFilterToCamelHeaders()");

        return false;
    }

    @Override
    public boolean applyFilterToExternalHeaders(String headerName
            , Object headerValue, Exchange exchange) {
        log.debug("applyFilterToExternalHeaders()");
        exchange.getIn().removeHeaders("kafka.HEADERS*");
        exchange.getIn().removeHeaders("camel*");
//        KafkaHeaderDeserializerHandler kafkaHeaderDeserializerHandler =
//              new KafkaHeaderDeserializerHandler();
//        System.out.println("headerName  -> "+headerName);
//        System.out.println("headerValue -> "+kafkaHeaderDeserializerHandler.deserialize(headerName, (byte[]) headerValue));
//        System.out.println(kafkaHeaderDeserializerHandler.deserialize(headerName, (byte[]) headerValue));
//        log.info("External Header: " + headerName + ":" + doApplyFilter(headerValue));
        return true;
    }

    private String doApplyFilter(Object headerValue) {
        if (headerValue != null) {
            try {
                byte[] bytes = (byte[])headerValue;
//                if(1==1) throw new Exception("TEST");
                if (bytes != null) {
                    String value = new String(bytes, StandardCharsets.UTF_8);
                    return "(byte[]): " + value;
                }

            } catch (ClassCastException c) {
                //Ignore and do default
            } catch (Throwable t) {
                log.error("Error when trying to convert bytes to string", t);
            }
            return "("+headerValue.getClass().getName()+"): "+ headerValue.toString();
        } else {
            return "null";
        }
    }
}
