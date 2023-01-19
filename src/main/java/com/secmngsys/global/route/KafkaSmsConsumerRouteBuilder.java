package com.secmngsys.global.route;

import com.secmngsys.global.configuration.kafka.KafkaProperties;
import com.secmngsys.global.process.KafkaDeadLetterChannelProcess;
import com.secmngsys.global.process.KafkaOffsetManagerProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaConfiguration;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.component.kafka.serde.KafkaHeaderSerializer;
import org.apache.camel.spi.HeaderFilterStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

import static org.apache.camel.LoggingLevel.ERROR;

@Slf4j
@Component
public class KafkaSmsConsumerRouteBuilder extends RouteBuilder { //extends GlobalRouteBuilder

    public static final String ROUTE_ID = "consumeFromKafka";
    //public static final String OUTGOING_ENDPOINT = "file://C:\\myWork\\secmngsys\\logs\\camelKafkaConsumer\\files";
    //private final KafkaConfiguration configuration;

    //@Autowired
    //@Qualifier("kafkaOffsetManager")
    private KafkaOffsetManagerProcessor kafkaOffsetManagerProcessor;
    private KafkaProperties kafkaProperties;

    public KafkaSmsConsumerRouteBuilder(KafkaOffsetManagerProcessor kafkaOffsetManagerProcessor
            , KafkaProperties kafkaProperties){
        this.kafkaOffsetManagerProcessor = kafkaOffsetManagerProcessor;
        this.kafkaProperties = kafkaProperties;
    }

//    @Autowired
//    private KafkaProperties kafkaProps;
//
//    @Autowired
//    private FooBar fooBarService;

    @Override
    public void configure() {

        String topic = (String) kafkaProperties.getTemplate().get("sms-topic");

        errorHandler(deadLetterChannel("direct:dead")
                .useOriginalMessage()
                .maximumRedeliveries(3)
                .redeliveryDelay(5000));

        from("direct:dead")
                .log(ERROR,"Sending Exception to MyErrorProcessor")
                .bean(KafkaDeadLetterChannelProcess.class) // Exception 정보 포함해서 DLQ로 전송
                .log("몬데이거 씨발")
                .to("kafka:"+topic+"-dlt?brokers=localhost:9092")
                ;

        String kafkaUrl = kafkaProperties.buildKafkaSmsUrl();
        log.info("building camel route to consume from kafka: {}", kafkaUrl);

        from(kafkaUrl)//+"&headerDeserializer=#kafkaHeaderDeserializerCustom")
                .routeId(ROUTE_ID)
                .process(exchange -> {
                    log.info(this.dumpKafkaDetails(exchange));
                })
                .process(exchange -> {
                    // do something interesting
//                    final HeaderFilterStrategy headerFilterStrategy = configuration.getHeaderFilterStrategy();
//                    final String key = entry.getKey();
//                    final Object value = entry.getValue();
//
//                    if (shouldBeFiltered(key, value, exchange, headerFilterStrategy)) {
//                        final KafkaHeaderSerializer headerSerializer = configuration.getHeaderSerializer();
//                        final byte[] headerValue = headerSerializer.serialize(key, value);
//
//                        if (headerValue == null) {
//                            return null;
//                        }
//                        return new RecordHeader(key, headerValue);
//                    }
                    System.out.println("exchange-getHeaders   ->"+exchange.getIn().getHeaders());
                    System.out.println("exchange-getBody      ->"+exchange.getIn().getBody());
                    System.out.println("exchange-getMessageId ->"+exchange.getIn().getMessageId());
                    System.out.println("exchange-getHeaders1   ->"+exchange.getMessage());
                    System.out.println("exchange-getHeaders2   ->"+exchange.getIn().getHeader(KafkaConstants.HEADERS,"testtest"));
                    System.out.println("---------------------------------------------------------------------");
                    System.out.println("exchange-getHeaders2   ->"+exchange.getIn().getHeader(KafkaConstants.HEADERS,"myHeader"));
                    System.out.println("exchange-getHeaders2   ->"+exchange.getIn().getHeader("myHeader"));
                    System.out.println("exchange-getHeaders2   ->"+exchange.getIn().getHeader(KafkaConstants.KEY));
                    System.out.println("exchange-getHeaders2   ->"+exchange.getIn().getMandatoryBody());
                    System.out.println("exchange-getHeaders2   ->"+exchange.getProperties());

                    //System.out.println("kafkaProperties.getEndpointOffset(): "+kafkaProperties.getEndpointOffset());
                    /*
                 .log("Message received from Kafka : ${body}")
                .log("    on the topic ${headers[kafka.TOPIC]}")
                .log("    on the partition ${headers[kafka.PARTITION]}")
                .log("    with the offset ${headers[kafka.OFFSET]}")
                .log("    with the key ${headers[kafka.KEY]}");
                     */
                })
                .process(exchange -> {
                    // simple approach to generating errors
                    String body = exchange.getIn().getBody(String.class);
                    if (body.startsWith("error")) {
                        throw new RuntimeException("can't handle the message");
                    }
                })
                .process(exchange -> {
                    // do something interesting
                    System.out.println("hihihihi2222222222");
                })
                .process(exchange -> {
                    System.out.println("Exchange.FILE_NAME - "+Exchange.FILE_NAME);
                    System.out.println("UUID.randomUUID() - "+UUID.randomUUID());
                   // exchange.setProperty(Exchange.FILE_NAME, UUID.randomUUID() + ".txt");
                    Long test = (Long) exchange.getIn().getHeader(KafkaConstants.OFFSET);
//                    if(test == 27) {
//                        System.out.println("test - " +test);
//                        throw new Exception();
//                    }
                    log.error("테스트시점");
                    //throw new Exception();

                })
                // manage the manual commit
                .process(exchange -> {
                    log.info("message is now> {}", exchange.getIn().getBody(String.class));
                    //log.info("kafkaProperties.getEndpointOffset() ", kafkaProperties.getEndpointOffset());
                    System.out.println("kafkaProperties.getEndpointOffset() - "+kafkaProperties.getEndpointOffset());
                })
                .to(kafkaProperties.getEndpointOffset())
                .process(kafkaOffsetManagerProcessor)
                .log("end");
    }

    private String dumpKafkaDetails(Exchange exchange) {
        StringBuilder sb = new StringBuilder();
        sb.append("Message Received from topic:").append(exchange.getIn().getHeader(KafkaConstants.TOPIC));
        sb.append("\r\n");
        sb.append("Message Received from partition:").append(exchange.getIn().getHeader(KafkaConstants.PARTITION));
        sb.append(" with partition key:").append(exchange.getIn().getHeader(KafkaConstants.PARTITION_KEY));
        sb.append("\r\n");
        sb.append("Message offset:").append(exchange.getIn().getHeader(KafkaConstants.OFFSET));
        sb.append("\r\n");
        sb.append("Message last record:").append(exchange.getIn().getHeader(KafkaConstants.LAST_RECORD_BEFORE_COMMIT));
        sb.append("\r\n");
        sb.append("Message Received:").append(exchange.getIn().getBody());
        sb.append("\r\n");
        return sb.toString();
    }
}
