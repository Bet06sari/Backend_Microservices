package com.kodlamaio.inventoryService.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.CarCreatedEvent;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FilterServiceProducer {
	private static final Logger LOGGER = LoggerFactory.getLogger(FilterServiceProducer.class);


    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(CarCreatedEvent event) {
        LOGGER.info(String.format("Car created event for filter-service => %s", event.toString()));

        Message<CarCreatedEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, "createdFilter").build();

        kafkaTemplate.send(message);
    }

//    public void sendMessage(CarUpdateEvent event) {
//        LOGGER.info(String.format("Car updated event for filter-service => %s", event.toString()));
//
//        Message<CarUpdateEvent> message = MessageBuilder
//                .withPayload(event)
//                .setHeader(KafkaHeaders.TOPIC, "createdFilter").build();
//
//        kafkaTemplate.send(message);
//    }
}
