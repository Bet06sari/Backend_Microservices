package com.kodlamaio.invoiceService.business.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllInvoicesResponse {
	private String id;
    private String fullName;
    private String brandName;
    private String modelName;
    private double dailyPrice;
    private int rentedForDays;
    private double totalPrice;

}
