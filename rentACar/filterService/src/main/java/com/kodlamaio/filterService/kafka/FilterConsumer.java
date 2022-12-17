package com.kodlamaio.filterService.kafka;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.BrandUpdatedEvent;
import com.kodlamaio.common.events.CarCreatedEvent;
import com.kodlamaio.common.events.CarDeletedEvent;
import com.kodlamaio.common.events.CarUpdateEvent;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.filterService.business.abstracts.FilterService;
import com.kodlamaio.filterService.entities.Filter;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class FilterConsumer {
	private static final Logger LOGGER = LoggerFactory.getLogger(FilterConsumer.class);
    private final FilterService filterService;
    private final ModelMapperService modelMapperService;

    @KafkaListener(
    		topics = "createdFilter"
            , groupId = "created_filter"
    )
    public void consume(CarCreatedEvent event) {
    	Filter carFilter = modelMapperService.forRequest().map(event, Filter.class);
        carFilter.setId(UUID.randomUUID().toString());
        filterService.save(carFilter);
        LOGGER.info("Inventory created event consumed: {}", event);
    }
    
    @KafkaListener(
            topics = "filter-updated"
            , groupId = "filterUpdate"
    )
    public void consume(CarUpdateEvent event) {
        Filter filter = modelMapperService.forRequest().map(event, Filter.class);
        String id = filterService.getByCarId(event.getCarId()).getId();
        filter.setId(id);
        filterService.save(filter);
        LOGGER.info("Inventory updated event consumed: {}", event);
    }

    @KafkaListener(
            topics = "filter-deleted"
            , groupId = "filterDelete"
    )
    public void consume(CarDeletedEvent event) {
        filterService.deleteByCarId(event.getCarId());
        LOGGER.info("Inventory updated event consumed: {}", event);
    }

    @KafkaListener(
            topics = "filter-brand-updated"
            , groupId = "filterUpdateBrandName"
    )
    public void consume(BrandUpdatedEvent event) {
        List<Filter> filters = filterService.getByBrandId(event.getId());
        for(Filter filter:filters){
            filter.setBrandName(event.getName());
            filter.setId(filter.getId());
            filterService.save(filter);
        }
        LOGGER.info("Inventory brandName updated event consumed: {}", event);
    }

    
}
