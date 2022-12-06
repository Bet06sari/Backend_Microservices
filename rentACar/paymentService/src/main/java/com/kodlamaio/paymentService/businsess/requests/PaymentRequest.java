package com.kodlamaio.paymentService.businsess.requests;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
	private String cardNumber;
	private String fullName;
    private String cardCvv;
    private double price;
}