package com.kodlamaio.filterService.business.abstracts;

import java.util.List;

import com.kodlamaio.filterService.business.responses.GetAllCarFiltersResponse;
import com.kodlamaio.filterService.entities.Filter;

public interface FilterService {
	void save(Filter filter);
    void delete(String id);
    List<GetAllCarFiltersResponse> getAll();
    List<GetAllCarFiltersResponse> getByBrandName(String brandName);
    List<GetAllCarFiltersResponse> getByModelName(String modelName);
    Filter getByCarId(String id);
    List<Filter> getByModelId(String modelId);
    List<Filter> getByBrandId(String brandId);
    void deleteByCarId(String id);
    List<Filter> getByBrandNameOrModelName(String name);
}
