package com.kodlamaio.inventoryService.api;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.inventoryService.business.abstracts.ModelService;
import com.kodlamaio.inventoryService.business.requests.create.CreateModelRequest;
import com.kodlamaio.inventoryService.business.requests.update.UpdateModelRequest;
import com.kodlamaio.inventoryService.business.responses.create.CreateModelResponse;
import com.kodlamaio.inventoryService.business.responses.get.GetAllModelResponse;
import com.kodlamaio.inventoryService.business.responses.update.UpdateModelResponse;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/models")
public class ModelController {
	
	private ModelService modelService;
	
	@GetMapping()
	public List<GetAllModelResponse> getAll(){
		return this.modelService.getAll();
	}
	
	@PostMapping()
	public CreateModelResponse add(@RequestBody CreateModelRequest createModelRequest) {
		return this.modelService.add(createModelRequest);
	}
	
	@DeleteMapping("/{id}")
	void delete(@PathVariable String id) {
		this.modelService.delete(id);
	}
	
	@PutMapping
	public UpdateModelResponse update (@RequestBody UpdateModelRequest updateModelRequest) {
		return this.modelService.update(updateModelRequest);
	}
}
