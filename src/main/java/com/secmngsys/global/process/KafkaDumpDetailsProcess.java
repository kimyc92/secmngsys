package com.secmngsys.global.process;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.kafka.KafkaConstants;

@Slf4j
public class KafkaDumpDetailsProcess implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        log.info(this.dumpKafkaDetails(exchange));
    }

    private String dumpKafkaDetails(Exchange exchange) {
        StringBuilder sb = new StringBuilder();
        sb.append("\r\n");
        sb.append("Message Received from topic:").append(exchange.getIn().getHeader(KafkaConstants.TOPIC));
        sb.append(" with topic key:").append(exchange.getIn().getHeader(KafkaConstants.KEY));
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
        sb.append("Message Headers:").append(exchange.getIn().getHeaders());
        sb.append("\r\n");
        return sb.toString();
    }
}

