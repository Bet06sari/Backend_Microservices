package com.kodlamaio.inventoryService.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.CarCreatedEvent;

@Service
public class FilterServiceProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(FilterServiceProducer.class);

    private final NewTopic topic;

    private final KafkaTemplate<String, CarCreatedEvent> kafkaTemplate;
    
    public FilterServiceProducer(NewTopic topic, KafkaTemplate<String, CarCreatedEvent> kafkaTemplate) {
		this.topic = topic;
		this.kafkaTemplate = kafkaTemplate;
	}

	public void sendMessage(CarCreatedEvent event) {
        LOGGER.info(String.format("Car created event for filter-service => %s", event.toString()));
        
        Message<CarCreatedEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, topic.name()).build();
        kafkaTemplate.send(message);
    }
}
