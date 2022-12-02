package com.kodlamaio.inventoryService.business.abstracts;

import java.util.List;


import com.kodlamaio.inventoryService.business.requests.create.CreateBrandRequest;
import com.kodlamaio.inventoryService.business.requests.update.UpdateBrandRequest;
import com.kodlamaio.inventoryService.business.responses.create.CreateBrandResponse;
import com.kodlamaio.inventoryService.business.responses.get.GetAllBrandResponse;
import com.kodlamaio.inventoryService.business.responses.update.UpdateBrandResponse;

public interface BrandService {
	
	List<GetAllBrandResponse> getAll();
	CreateBrandResponse add(CreateBrandRequest createBrandRequest);
	void delete(String id);
	UpdateBrandResponse update (UpdateBrandRequest updateBrandRequest);
}

