package com.kodlamaio.inventoryService.business.abstracts;

import java.util.List;

import com.kodlamaio.common.responses.GetAllCarResponse;
import com.kodlamaio.inventoryService.business.requests.create.CreateCarRequest;
import com.kodlamaio.inventoryService.business.requests.update.UpdateCarRequest;
import com.kodlamaio.inventoryService.business.responses.create.CreateCarResponse;
import com.kodlamaio.inventoryService.business.responses.update.UpdateCarResponse;

public interface CarService {
	List<GetAllCarResponse> getAll();
	CreateCarResponse add(CreateCarRequest createCarRequest);
	void delete(String id);
	UpdateCarResponse update (UpdateCarRequest updateCarRequest);
	void changeCarState(String id);
	void changeCarState(String oldCarId, String newCarId);
	void checkIfCarAvailable(String id);
	
	GetAllCarResponse getById(String carId);
}
