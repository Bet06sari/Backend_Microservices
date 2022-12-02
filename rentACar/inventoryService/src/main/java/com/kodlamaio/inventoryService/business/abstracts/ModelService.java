package com.kodlamaio.inventoryService.business.abstracts;

import java.util.List;

import com.kodlamaio.inventoryService.business.requests.create.CreateModelRequest;
import com.kodlamaio.inventoryService.business.requests.update.UpdateModelRequest;
import com.kodlamaio.inventoryService.business.responses.create.CreateModelResponse;
import com.kodlamaio.inventoryService.business.responses.get.GetAllModelResponse;
import com.kodlamaio.inventoryService.business.responses.update.UpdateModelResponse;

public interface ModelService {
	List<GetAllModelResponse> getAll();
	CreateModelResponse add(CreateModelRequest createModelRequest);
	void delete(String id);
	UpdateModelResponse update (UpdateModelRequest updateModelRequest);

}
