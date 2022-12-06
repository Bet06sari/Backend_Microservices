package com.kodlamaio.paymentService.businsess.abstracts;

import com.kodlamaio.paymentService.businsess.requests.CreatePaymentRequest;
import com.kodlamaio.paymentService.businsess.requests.PaymentRequest;
import com.kodlamaio.paymentService.businsess.responses.CreatePaymentResponse;

public interface PaymentService {
	CreatePaymentResponse add(CreatePaymentRequest request);
	void checkIfPaymentSuccessful(PaymentRequest request);
	
}
