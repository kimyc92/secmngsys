package com.secmngsys.global.process;

import com.secmngsys.global.configuration.code.KafkaLogFileTypeCode;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.kafka.KafkaConstants;

public class KafkaConsumerLogFileProcess implements Processor {

    protected final KafkaLogFileTypeCode kafkaLogFileTypeCode;

    public KafkaConsumerLogFileProcess(KafkaLogFileTypeCode kafkaLogFileTypeCode) {
        this.kafkaLogFileTypeCode = kafkaLogFileTypeCode;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        StringBuilder fileName = new StringBuilder();
        StringBuilder fileContent = new StringBuilder();

        fileName.append(exchange.getIn().getHeader(KafkaConstants.TOPIC)+"_");
        fileName.append(String.format("%04d", exchange.getIn().getHeader(KafkaConstants.PARTITION))+"_");
        fileName.append(String.format("%016d", exchange.getIn().getHeader(KafkaConstants.OFFSET)));

        if(KafkaLogFileTypeCode.Headers == kafkaLogFileTypeCode) {
            exchange.getIn().removeHeaders("kafka.*");
            exchange.getIn().removeHeaders("camel*");
            fileContent.append(exchange.getIn().getHeaders());
            fileName.append("_Headers");
        }

        if(KafkaLogFileTypeCode.Key == kafkaLogFileTypeCode) {
            fileContent.append(exchange.getIn().getHeader(KafkaConstants.KEY));
            fileName.append("_Key");
        }

        if(KafkaLogFileTypeCode.Value == kafkaLogFileTypeCode) {
            fileContent.append(exchange.getIn().getBody(String.class));
            fileName.append("_Value");
        }

        exchange.getIn().setHeader(Exchange.FILE_NAME, fileName);
        exchange.getIn().setBody(fileContent);
    }
}
