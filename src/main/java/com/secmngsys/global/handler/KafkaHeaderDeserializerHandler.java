package com.secmngsys.global.handler;

import org.apache.camel.component.kafka.serde.KafkaHeaderDeserializer;

import java.nio.charset.StandardCharsets;

public class KafkaHeaderDeserializerHandler implements KafkaHeaderDeserializer {

    /*
        Kafka Header Byte To String Deserializer Handler
     */
    @Override
    public Object deserialize(String key, byte[] value) {
        return new String(value, StandardCharsets.UTF_8);
    }

}
