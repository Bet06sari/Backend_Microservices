package com.kodlamaio.paymentService.businsess.concretes;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.paymentService.businsess.abstracts.PaymentService;
import com.kodlamaio.paymentService.businsess.requests.CreatePaymentRequest;
import com.kodlamaio.paymentService.businsess.responses.CreatePaymentResponse;
import com.kodlamaio.paymentService.businsess.responses.GetAllPaymentsResponse;
import com.kodlamaio.paymentService.dataAccess.PaymentRepository;
import com.kodlamaio.paymentService.entities.Payment;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PaymentManager implements PaymentService{
	private PaymentRepository paymentRepository;
	private ModelMapperService modelMapperService;

	@Override
	public CreatePaymentResponse add(CreatePaymentRequest request) {
		Payment payment = this.modelMapperService.forRequest().map(request, Payment.class);
		payment.setId(UUID.randomUUID().toString());
		this.paymentRepository.save(payment);
		CreatePaymentResponse createPaymentResponse = this.modelMapperService.forResponse().map(payment,
				CreatePaymentResponse.class);

		return createPaymentResponse;
	}

	@Override
	public List<GetAllPaymentsResponse> getAll() {
		return paymentRepository.findAll().stream().
				map(payment -> modelMapperService.forResponse().
						map(payment, GetAllPaymentsResponse.class)).collect(Collectors.toList());
	}



}
