package com.kodlamaio.filterService.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.filterService.business.abstracts.FilterService;
import com.kodlamaio.filterService.business.responses.GetAllFiltersResponse;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/filters")
public class FiltersController {
	 private final FilterService service;

	    @GetMapping
	    public List<GetAllFiltersResponse> getAll() {
	        return service.getAll();
	    }

	    @GetMapping("/brand")
	    public List<GetAllFiltersResponse> getByBrandName(@RequestParam String brandName) {
	        return service.getByBrandName(brandName);
	    }

	    @GetMapping("/model")
	    public List<GetAllFiltersResponse> getByModelName(@RequestParam String modelName) {
	        return service.getByModelName(modelName);
	    }

	    @GetMapping("/plate")
	    public List<GetAllFiltersResponse> getByPlate(@RequestParam String plate) {
	        return service.getByPlate(plate);
	    }

	    @GetMapping("/plate-search")
	    public List<GetAllFiltersResponse> searchByPlate(@RequestParam String plate) {
	        return service.searchByPlate(plate);
	    }

	    @GetMapping("/brand-search")
	    public List<GetAllFiltersResponse> searchByBrandName(@RequestParam String brandName) {
	        return service.searchByBrandName(brandName);
	    }

	    @GetMapping("/model-search")
	    public List<GetAllFiltersResponse> searchByModelName(@RequestParam String modelName) {
	        return service.searchByModelName(modelName);
	    }

	    @GetMapping("/year")
	    public List<GetAllFiltersResponse> getByModelYear(@RequestParam int modelYear) {
	        return service.getByModelYear(modelYear);
	    }

	    @GetMapping("/state")
	    public List<GetAllFiltersResponse> getByState(@RequestParam int state) {
	        return service.getByState(state);
	    }
}