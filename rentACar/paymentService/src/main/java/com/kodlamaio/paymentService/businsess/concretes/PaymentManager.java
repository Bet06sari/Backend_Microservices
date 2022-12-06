package com.kodlamaio.paymentService.businsess.concretes;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.utilities.exceptions.BusinessException;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.paymentService.businsess.abstracts.PaymentService;
import com.kodlamaio.paymentService.businsess.abstracts.PosService;
import com.kodlamaio.paymentService.businsess.requests.CreatePaymentRequest;
import com.kodlamaio.paymentService.businsess.requests.PaymentRequest;
import com.kodlamaio.paymentService.businsess.responses.CreatePaymentResponse;
import com.kodlamaio.paymentService.dataAccess.PaymentRepository;
import com.kodlamaio.paymentService.entities.Payment;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PaymentManager implements PaymentService{
	private PaymentRepository paymentRepository;
	private ModelMapperService modelMapperService;
	private final PosService posService;

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
	public void checkIfPaymentSuccessful(PaymentRequest request) {
		checkPayment(request);
	}
	
	private void checkPayment(PaymentRequest request) {
        if (!paymentRepository.existsByCardNumberAndFullNameAndCardCvv(request.getCardNumber(), request.getFullName(), request.getCardCvv())) {
            throw new BusinessException("Invalid payment");
        }
        double balance = paymentRepository.findByCardNumber(request.getCardNumber()).getBalance();
        if (balance < request.getPrice()) {
            throw new BusinessException("No enough money");
        }
        posService.pay(); // Fake payment
        Payment payment = paymentRepository.findByCardNumber(request.getCardNumber());
        payment.setBalance(balance - request.getPrice());
        paymentRepository.save(payment);
    }

}
