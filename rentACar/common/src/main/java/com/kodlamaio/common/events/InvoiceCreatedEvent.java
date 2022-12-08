package com.kodlamaio.common.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceCreatedEvent {
	private String fullName;
	private String brandName;
	private String modelName;
	private double dailyPrice;
	private int rentedForDays;
	private double totalPrice;
}
