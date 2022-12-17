package com.kodlamaio.filterService.dataAccess;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kodlamaio.filterService.entities.Filter;

public interface FilterRepository extends MongoRepository<Filter,String> {
	List<Filter> findByBrandNameContainingIgnoreCase(String brandName);
    List<Filter> findByModelNameContainingIgnoreCase(String brandName);
    Filter findByCarId(String carId);
    Filter findByModelId(String modelId);
    List<Filter> findAllByModelId(String modelId);
    List<Filter> findAllByBrandId(String modelId);
    void deleteByCarId(String carId);
    List<Filter> findAllByModelNameIgnoreCaseOrBrandNameIgnoreCase(String name);

}
