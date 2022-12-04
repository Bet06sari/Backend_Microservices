package com.kodlamaio.paymentService.businsess.abstracts;

import java.util.List;

import com.kodlamaio.paymentService.businsess.requests.CreatePaymentRequest;
import com.kodlamaio.paymentService.businsess.responses.CreatePaymentResponse;
import com.kodlamaio.paymentService.businsess.responses.GetAllPaymentsResponse;

public interface PaymentService {
	List<GetAllPaymentsResponse> getAll();
	CreatePaymentResponse add(CreatePaymentRequest request);
	
}
