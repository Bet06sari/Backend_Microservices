package com.kodlamaio.inventoryService.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.common.responses.GetAllCarResponse;
import com.kodlamaio.inventoryService.business.abstracts.CarService;
import com.kodlamaio.inventoryService.business.requests.create.CreateCarRequest;
import com.kodlamaio.inventoryService.business.requests.update.UpdateCarRequest;
import com.kodlamaio.inventoryService.business.responses.create.CreateCarResponse;
import com.kodlamaio.inventoryService.business.responses.update.UpdateCarResponse;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/cars")

public class CarController {
	
	private CarService carService;
	
	@GetMapping()
	public List<GetAllCarResponse> getAll(){
		return this.carService.getAll();
	}
	
	@PostMapping()
	@ResponseStatus(value = HttpStatus.CREATED)
	public CreateCarResponse add(@RequestBody CreateCarRequest createCarRequest) {
		return this.carService.add(createCarRequest);
	}
	
	 @DeleteMapping("/{id}")
	 @ResponseStatus(value = HttpStatus.NO_CONTENT)
	void delete(@PathVariable String id) {
		 carService.delete(id);
	}
	
	@PutMapping("/{id}")
	public UpdateCarResponse update (@RequestBody UpdateCarRequest updateCarRequest) {
		return this.carService.update(updateCarRequest);
	}
	
	 @GetMapping("/checkIfCarAvailable/{id}")
	 public void checkIfCarAvailable(@PathVariable String id) {
		 carService.checkIfCarAvailable(id);
	 }
	 
	 @GetMapping("/{id}")
	    public GetAllCarResponse getById(@PathVariable String id) {
	        return carService.getById(id);
	 }
	
}
