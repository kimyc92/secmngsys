package com.secmngsys.global.route;

import com.secmngsys.global.configuration.code.KafkaLogFileTypeCode;
import com.secmngsys.global.configuration.kafka.KafkaProperties;
import com.secmngsys.global.process.KafkaConsumerLogFileProcess;
import com.secmngsys.global.process.KafkaOffsetManagerProcessor;
import org.apache.camel.Endpoint;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicReference;

import static org.apache.camel.LoggingLevel.INFO;

@Component
public class KafkaLogFileRoute extends RouteBuilder {

    private KafkaProperties kafkaProperties;

    public KafkaLogFileRoute(KafkaProperties kafkaProperties){
        this.kafkaProperties = kafkaProperties;
    }

    @Override
    public void configure() throws Exception {

        from("direct:createKafkaLogFile")
                .log(INFO, "createKafkaLogFile")
                .process(exchange -> {
                    String topic = (String) exchange.getIn().getHeader(KafkaConstants.TOPIC);
                    String filePermission = kafkaProperties.getEndpointLogFilePermission();
                    String offsetLogDir = kafkaProperties.getEndpointConsumerOffsetLogDir() + topic
                            + "?chmod=" + filePermission + "&chmodDirectory=" + filePermission;
                    exchange.setProperty("offsetLogDir", offsetLogDir);
                })
                .process(new KafkaConsumerLogFileProcess(KafkaLogFileTypeCode.Value)).toD("${exchangeProperty.offsetLogDir}")
                .process(new KafkaConsumerLogFileProcess(KafkaLogFileTypeCode.Key)).toD("${exchangeProperty.offsetLogDir}")
                .process(new KafkaConsumerLogFileProcess(KafkaLogFileTypeCode.Headers)).toD("${exchangeProperty.offsetLogDir}")
                .removeProperty("offsetLogDir")
        ;

    }

}
