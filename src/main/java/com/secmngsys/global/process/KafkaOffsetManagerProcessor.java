package com.secmngsys.global.process;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.component.kafka.consumer.KafkaManualCommit;
import org.springframework.stereotype.Component;

@Slf4j
@Component("kafkaOffsetManager")
public class KafkaOffsetManagerProcessor implements Processor {

    @Override
    public void process(Exchange exchange) {
        Boolean lastOne = exchange.getIn().getHeader(KafkaConstants.LAST_RECORD_BEFORE_COMMIT, Boolean.class);
        //System.out.println("마지막 한개? - "+lastOne);
        if (lastOne != null && lastOne) {
            // 헤더에 MANUAL_COMMIT이 True 일 경우
            KafkaManualCommit manual =
                    exchange.getIn().getHeader(KafkaConstants.MANUAL_COMMIT, KafkaManualCommit.class);
           // System.out.println("manual - "+manual);
            if (manual != null) {
                log.info("manually committing the offset for batch");
                manual.commit();
            }
        } else {
            log.info("NOT time to commit the offset yet");
        }
    }
}
