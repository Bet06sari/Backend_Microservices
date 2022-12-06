package com.kodlamaio.paymentService.businsess.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllPaymentsResponse {
	private String id;
    private String cardNumber;
    private String cardCvv;
    private double balance;
}
