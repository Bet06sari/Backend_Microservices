package com.kodlamaoi.rentalService.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.InvoiceCreatedEvent;
import com.kodlamaio.common.events.RentalCreatedEvent;
import com.kodlamaio.common.events.RentalUpdatedCarEvent;

@Service
public class RentalProducer {
	private static final Logger LOGGER = LoggerFactory.getLogger(RentalProducer.class);
	private NewTopic topic;
	private KafkaTemplate<String, Object> kafkaTemplate;
	
	public RentalProducer(NewTopic topic, KafkaTemplate<String, Object> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }
    public void sendMessage(RentalCreatedEvent rentalCreatedEvent) {
        LOGGER.info(String.format("Rental created event => %s", rentalCreatedEvent.toString()));

        Message<RentalCreatedEvent> message = MessageBuilder
                .withPayload(rentalCreatedEvent)
                .setHeader(KafkaHeaders.TOPIC, "rental-create").build();

        kafkaTemplate.send(message);
    }

    public void sendMessage(RentalUpdatedCarEvent rentalUpdatedEvent) {
        LOGGER.info(String.format("Rental updated event => %s", rentalUpdatedEvent.toString()));

        Message<RentalUpdatedCarEvent> message = MessageBuilder
                .withPayload(rentalUpdatedEvent)
                .setHeader(KafkaHeaders.TOPIC, "rental-update").build();

        kafkaTemplate.send(message);
    }

//    public void sendMessage(InvoiceCreatedEvent invoiceCreateEvent) {
//        LOGGER.info(String.format("Rental invoice event => %s", invoiceCreateEvent.toString()));
//
//        Message<InvoiceCreatedEvent> message = MessageBuilder
//                .withPayload(invoiceCreateEvent)
//                .setHeader(KafkaHeaders.TOPIC, topic.name()).build();
//
//        kafkaTemplate.send(message);
//    }
}

