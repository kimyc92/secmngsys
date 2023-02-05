package com.secmngsys.global.route;

import com.secmngsys.global.configuration.code.KafkaLogFileTypeCode;
import com.secmngsys.global.configuration.kafka.KafkaConfig;
import com.secmngsys.global.configuration.kafka.KafkaProperties;
import com.secmngsys.global.handler.KafkaHeaderFilterHandler;
import com.secmngsys.global.process.KafkaConsumerLogFileProcess;
import com.secmngsys.global.process.KafkaDeadLetterChannelProcess;
import com.secmngsys.global.process.KafkaDumpDetailsProcess;
import com.secmngsys.global.process.KafkaOffsetManagerProcessor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.spi.HeaderFilterStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static org.apache.camel.LoggingLevel.ERROR;
import static org.apache.camel.LoggingLevel.INFO;

@Slf4j
@Component
@ConditionalOnProperty(value = "mode.kafka", havingValue = "true", matchIfMissing = false)
public class KafkaSmsConsumerRouteBuilder extends RouteBuilder { //extends GlobalRouteBuilder

    public static final String ROUTE_ID = "consumeFromKafka";
    //public static final String OUTGOING_ENDPOINT = "file://C:\\myWork\\secmngsys\\logs\\camelKafkaConsumer\\files";
    //private final KafkaConfiguration configuration;

    //@Autowired
    //@Qualifier("kafkaOffsetManager")
    private KafkaOffsetManagerProcessor kafkaOffsetManagerProcessor;
    private KafkaProperties kafkaProperties;
    KafkaConfig kafkaConfig;

    public KafkaSmsConsumerRouteBuilder(KafkaOffsetManagerProcessor kafkaOffsetManagerProcessor
            , KafkaProperties kafkaProperties, KafkaConfig kafkaConfig){
        this.kafkaOffsetManagerProcessor = kafkaOffsetManagerProcessor;
        this.kafkaProperties = kafkaProperties;
        this.kafkaConfig = kafkaConfig;
    }


    @Override
    public void configure() {

        String topic = (String) kafkaProperties.getTemplate().get("sms-topic");

        errorHandler(deadLetterChannel("direct:dead")
                .useOriginalMessage()
                .maximumRedeliveries(3)
                .redeliveryDelay(5000))
        ;

        from("direct:dead")
                .log(ERROR,"Sending Exception to MyErrorProcessor")
                .bean(KafkaDeadLetterChannelProcess.class) // Exception 정보 포함해서 DLQ로 전송
                .to("kafka:"+topic+"-dlt?brokers=localhost:29092")
        ;

        String kafkaUrl = kafkaConfig.buildKafkaSmsUrl();
        log.info("building camel route to consume from kafka: {}", kafkaUrl);
       // HeaderFilterStrategy headerFilterStrategy = getEndpoint().getHeaderFilterStrategy();
        from(kafkaUrl)
                .routeId(ROUTE_ID)
                .process(new KafkaDumpDetailsProcess())
                .process(exchange -> {
                    // do something interesting


//                    final String key = entry.getKey();
//                    final Object value = entry.getValue();
//                    if (shouldBeFiltered(key, value, exchange, headerFilterStrategy)) {
//                        final KafkaHeaderSerializer headerSerializer = configuration.getHeaderSerializer();
//                        final byte[] headerValue = headerSerializer.serialize(key, value);
//                        if (headerValue == null) {
//                            return null;
//                        }
//                        return new RecordHeader(key, headerValue);
//                    }
                    HeaderFilterStrategy strategy = new KafkaHeaderFilterHandler();
                    for (Map.Entry<String, Object> entry : exchange.getIn().getHeaders().entrySet()) {
                        String headerValue = exchange.getIn().getHeader(entry.getKey(), String.class);
                        System.out.println("[마이확인]-"+headerValue);

                        if (strategy != null && !strategy.applyFilterToCamelHeaders(entry.getKey(), headerValue, exchange)) {
                            if (log.isTraceEnabled()) {
                                log.trace("Adding header {} = {}", entry.getKey(), headerValue);
                            }
                           // builder.addHeader(entry.getKey(), headerValue);
                        }
                    }
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
                })
                .process(exchange -> {
                   // exchange.setProperty(Exchange.FILE_NAME, UUID.randomUUID() + ".txt");
                    Long test = (Long) exchange.getIn().getHeader(KafkaConstants.OFFSET);
//                    if(test == 27) {
//                        System.out.println("test - " +test);
//                        throw new Exception();
//                    }
                    log.error("테스트시점");
                })
                .log(INFO, "message is now> {}", String.valueOf(body()))
                .to("direct:createKafkaLogFile")
                .process(kafkaOffsetManagerProcessor)
        ;
    }

}
