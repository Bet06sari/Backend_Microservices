package com.kodlamaio.paymentService.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.paymentService.businsess.abstracts.PaymentService;
import com.kodlamaio.paymentService.businsess.requests.CreatePaymentRequest;
import com.kodlamaio.paymentService.businsess.requests.PaymentRequest;
import com.kodlamaio.paymentService.businsess.responses.CreatePaymentResponse;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/payments")
@AllArgsConstructor
public class PaymentController {

	private PaymentService paymentService;

	@PostMapping
	public CreatePaymentResponse add(@RequestBody CreatePaymentRequest createPaymentRequest) {
		return paymentService.add(createPaymentRequest);
	}

	@PostMapping("/check")
	public void checkIfPaymentSuccessful(@RequestParam String cardNumber, @RequestParam String fullName,
			@RequestParam String cardCvv, @RequestParam double price) {
		PaymentRequest request = new PaymentRequest(cardNumber, fullName, cardCvv, price);
		paymentService.checkIfPaymentSuccessful(request);
	}
}
