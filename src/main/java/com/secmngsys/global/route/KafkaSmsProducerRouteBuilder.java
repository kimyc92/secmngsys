package com.secmngsys.global.route;

import com.secmngsys.global.configuration.kafka.KafkaProperties;
import com.secmngsys.global.process.KafkaOffsetManagerProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Expression;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.apache.camel.LoggingLevel.ERROR;

@Slf4j
@Component
public class KafkaSmsProducerRouteBuilder extends RouteBuilder {

    private KafkaProperties kafkaProperties;

    public KafkaSmsProducerRouteBuilder(KafkaProperties kafkaProperties){
        this.kafkaProperties = kafkaProperties;
    }

    @Override
    public void configure() {

        String topic = (String) kafkaProperties.getTemplate().get("sms-topic");

        Date today = new Date();
        Locale currentLocale = new Locale("KOREAN", "KOREA");
        String pattern = "yyyyMMddHHmmss"; //hhmmss로 시간,분,초만 뽑기도 가능
        SimpleDateFormat formatter = new SimpleDateFormat(pattern,
                currentLocale);

        from("direct:kafka-sms-topic")
                .log(ERROR,"kafka-sms-topic Id: ${header.id}, User Received: ${body}")
                //.setBody(constant("Message from Camel" + formatter.format(today)))          // Message to send
                .setHeader("myHeader", constant("MY_HEADER_VALUE"))
                .setHeader(KafkaConstants.HEADERS, constant("testtest")) // Key of the message
                //.setHeader(KafkaConstants.HEADERS, constant("test1234", "zzzzzz"))
                //.setHeader("randomNumber", "zzzzzzzz")
                .setBody(body())          // Message to send
                .setHeader(KafkaConstants.KEY, constant("Camel")) // Key of the message
                .to("kafka:"+topic+"?brokers=localhost:9092")
                .log("Message received from Kafka : ${body}")
                .log("    on the topic ${headers[kafka.TOPIC]}")
                .log("    on the partition ${headers[kafka.PARTITION]}")
                .log("    with the offset ${headers[kafka.OFFSET]}")
                .log("    with the key ${headers[kafka.KEY]}");
    }
}
