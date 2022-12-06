package com.kodlamaio.common.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentRequest {
	private String cardNumber;
	private String fullName;
	private String cardCvv;
	private double balance;
}
