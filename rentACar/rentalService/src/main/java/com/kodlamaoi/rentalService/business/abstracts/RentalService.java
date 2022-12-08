package com.kodlamaoi.rentalService.business.abstracts;

import java.util.List;

import com.kodlamaio.common.requests.CreatePaymentRequest;
import com.kodlamaoi.rentalService.business.request.CreateRentalRequest;
import com.kodlamaoi.rentalService.business.request.UpdateRentalRequest;
import com.kodlamaoi.rentalService.business.response.CreateRentalResponse;
import com.kodlamaoi.rentalService.business.response.GetAllRentalResponse;
import com.kodlamaoi.rentalService.business.response.UpdateRentalResponse;

public interface RentalService {

	List<GetAllRentalResponse> getAll();
	CreateRentalResponse add(CreateRentalRequest createRentalRequest, CreatePaymentRequest createPaymentRequest);
	UpdateRentalResponse update(String id,UpdateRentalRequest updateRentalRequest);
	void delete(String id);
	
}
