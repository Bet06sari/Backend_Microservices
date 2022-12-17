package com.kodlamaio.inventoryService.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.RentalCreatedEvent;
import com.kodlamaio.common.events.RentalUpdatedCarEvent;
import com.kodlamaio.inventoryService.business.abstracts.CarService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RentalConsumer {
	private static final Logger LOGGER = LoggerFactory.getLogger(RentalConsumer.class);
	CarService carService;
	//daha önce gönderilen topic adını al carın id si, consumerların grup id si, grupId hangisinin grup eventi al gibi
	
	@KafkaListener(
            topics = "rental-create"
            ,groupId = "rentalCreate"
    )
	
	//update için de bu tarzda bir kod yazabiliriz
    public void consume(RentalCreatedEvent event){
        LOGGER.info(String.format("Order event received in stock service => %s", event.toString()));
        
        carService.changeCarState(event.getCarId(),2);
        LOGGER.info("Car rented");
    }
	
	@KafkaListener(
			topics = "rental-update"
			, groupId = "rentalUpdate"
	)
    public void consume(RentalUpdatedCarEvent RentalUpdatedCarEvent) {
        LOGGER.info(String.format("Order event received in stock service => %s", RentalUpdatedCarEvent.toString()));
        LOGGER.info("Car state changed");
        carService.changeCarState(RentalUpdatedCarEvent.getNewCarId(), 2);
        carService.changeCarState(RentalUpdatedCarEvent.getOldCarId(), 1);
        LOGGER.info("Cars state changed");
        // save the order event into the database
    }
	
}
