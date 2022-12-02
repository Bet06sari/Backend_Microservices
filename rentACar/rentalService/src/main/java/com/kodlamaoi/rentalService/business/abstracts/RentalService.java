package com.kodlamaoi.rentalService.business.abstracts;

import java.util.List;

import com.kodlamaoi.rentalService.business.request.CreateRentalRequest;
import com.kodlamaoi.rentalService.business.request.UpdateRentalRequest;
import com.kodlamaoi.rentalService.business.response.CreateRentalResponse;
import com.kodlamaoi.rentalService.business.response.GetAllRentalResponse;
import com.kodlamaoi.rentalService.business.response.UpdateRentalResponse;

public interface RentalService {

	List<GetAllRentalResponse> getAll();
	CreateRentalResponse add(CreateRentalRequest createRentalRequest);
	UpdateRentalResponse update(UpdateRentalRequest updateRentalRequest);
	void delete(String id);
}
