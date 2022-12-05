package com.kodlamaio.filterService.business.concretes;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.filterService.business.abstracts.FilterService;
import com.kodlamaio.filterService.business.responses.GetAllFiltersResponse;
import com.kodlamaio.filterService.dataAccess.FilterRepository;
import com.kodlamaio.filterService.entities.Filter;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FilterManager implements FilterService{
	private FilterRepository repository;
    private ModelMapperService mapper;

	@Override
	public List<GetAllFiltersResponse> getAll() {
		 List<Filter> filters = repository.findAll();
	        List<GetAllFiltersResponse> response = filters.stream()
	                .map(filter -> mapper.forResponse().
	                		map(filter, GetAllFiltersResponse.class)).toList();
	        return response;
	}

	@Override
	public List<GetAllFiltersResponse> getByBrandName(String brandName) {
		List<Filter> filters = repository.findByBrandNameIgnoreCase(brandName);
        List<GetAllFiltersResponse> response = filters.stream()
                .map(filter -> mapper.forResponse()
                		.map(filter, GetAllFiltersResponse.class)).toList();
        return response;
	}

	@Override
	public List<GetAllFiltersResponse> getByModelName(String modelName) {
		List<Filter> filters = repository.findByModelNameIgnoreCase(modelName);
        List<GetAllFiltersResponse> response = filters.stream()
                .map(filter -> mapper.forResponse().
                		map(filter, GetAllFiltersResponse.class)).toList();
        return response;
	}

	@Override
	public List<GetAllFiltersResponse> getByPlate(String plate) {
		List<Filter> filters = repository.findByPlateIgnoreCase(plate);
        List<GetAllFiltersResponse> response = filters.stream()
                .map(filter -> mapper.forResponse()
                		.map(filter, GetAllFiltersResponse.class)).toList();
        return response;
	}

	@Override
	public List<GetAllFiltersResponse> searchByPlate(String plate) {
		List<Filter> filters = repository.findByPlateContainingIgnoreCase(plate);
        List<GetAllFiltersResponse> response = filters.stream()
                .map(filter -> mapper.forResponse()
                		.map(filter, GetAllFiltersResponse.class)).toList();
        return response;
	}

	@Override
	public List<GetAllFiltersResponse> searchByBrandName(String brandName) {
		List<Filter> filters = repository.findByBrandNameContainingIgnoreCase(brandName);
        List<GetAllFiltersResponse> response = filters.stream()
                .map(filter -> mapper.forResponse()
                		.map(filter, GetAllFiltersResponse.class)).toList();
        return response;
	}

	@Override
	public List<GetAllFiltersResponse> searchByModelName(String modelName) {
		List<Filter> filters = repository.findByModelNameContainingIgnoreCase(modelName);
        List<GetAllFiltersResponse> response = filters.stream()
                .map(filter -> mapper.forResponse()
                		.map(filter, GetAllFiltersResponse.class)).toList();
        return response;
	}

	@Override
	public List<GetAllFiltersResponse> getByModelYear(int modelYear) {
		List<Filter> filters = repository.findByModelYear(modelYear);
        List<GetAllFiltersResponse> response = filters.stream()
                .map(filter -> mapper.forResponse()
                		.map(filter, GetAllFiltersResponse.class)).toList();
        return response;
	}

	@Override
	public List<GetAllFiltersResponse> getByState(int state) {
		List<Filter> filters = repository.findByState(state);
        List<GetAllFiltersResponse> response = filters.stream()
                .map(filter -> mapper.forResponse()
                		.map(filter, GetAllFiltersResponse.class)).toList();
        return response;
	}

	@Override
	public Filter getByCarId(String id) {
		return repository.findByCarId(id);
	}

	@Override
	public List<Filter> getByModelId(String modelId) {
		return repository.findByModelId(modelId);
	}

	@Override
	public List<Filter> getByBrandId(String brandId) {
		return repository.findByBrandId(brandId);
	}

	@Override
	public void save(Filter mongodb) {
		repository.save(mongodb);
		
	}

	@Override
	public void delete(String id) {
		repository.deleteByCarId(id);
		
	}

	@Override
	public void deleteAllByBrandId(String brandId) {
		repository.deleteAllByBrandId(brandId);
		
	}

	@Override
	public void deleteAllByModelId(String modelId) {
		repository.deleteAllByModelId(modelId);	
	}
}
