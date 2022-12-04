package com.kodlamaio.paymentService.businsess.responses;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllPaymentsResponse {
	private String id;
	private String rentalId;
	private String cardNo;
	private String cardHolder;
	private int cvv;
	private LocalDate cardDate;
	private double balance;
	private int status;

}
