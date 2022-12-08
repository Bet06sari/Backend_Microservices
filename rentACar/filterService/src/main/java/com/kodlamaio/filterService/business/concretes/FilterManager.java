package com.kodlamaio.filterService.business.concretes;



import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.filterService.business.abstracts.FilterService;
import com.kodlamaio.filterService.business.responses.GetAllCarFiltersResponse;
import com.kodlamaio.filterService.dataAccess.FilterRepository;
import com.kodlamaio.filterService.entities.Filter;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class FilterManager implements FilterService{
	private FilterRepository filterRepository;
    private ModelMapperService modelMapperService;
    
	@Override
	public void save(Filter filter) {
		filterRepository.save(filter);
		
	}

	@Override
	public void delete(String id) {
		filterRepository.deleteById(id);
		
	}

	@Override
	public List<GetAllCarFiltersResponse> getAll() {
		List<Filter> filters = filterRepository.findAll();
        List<GetAllCarFiltersResponse> response = filters
                .stream().map(filter -> modelMapperService.forResponse().map(filter,GetAllCarFiltersResponse.class))
                .collect(Collectors.toList());
        return response;
	}

	@Override
	public List<GetAllCarFiltersResponse> getByBrandName(String brandName) {
		List<Filter> filters = filterRepository.findByBrandNameContainingIgnoreCase(brandName);
        List<GetAllCarFiltersResponse> response = filters
                .stream()
                .map(filter -> modelMapperService.forResponse().map(filter, GetAllCarFiltersResponse.class))
                .collect(Collectors.toList());
        return response;
	}

	@Override
	public List<GetAllCarFiltersResponse> getByModelName(String modelName) {
		List<Filter> filters = filterRepository.findByModelNameContainingIgnoreCase(modelName);
        List<GetAllCarFiltersResponse> response = filters
                .stream()
                .map(filter -> modelMapperService.forResponse().map(filter, GetAllCarFiltersResponse.class))
                .collect(Collectors.toList());
        return response;
	}

	@Override
	public Filter getByCarId(String id) {
		return filterRepository.findByCarId(id);
	}

	@Override
	public List<Filter> getByModelId(String modelId) {
		return filterRepository.findAllByModelId(modelId);
	}

	@Override
	public List<Filter> getByBrandId(String brandId) {
		return filterRepository.findAllByModelId(brandId);
	}

	@Override
	public void deleteByCarId(String id) {
		filterRepository.deleteByCarId(id);
		
	}

}
