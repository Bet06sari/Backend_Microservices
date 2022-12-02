package com.kodlamaoi.rentalService.business.request;

import java.time.LocalDateTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRentalRequest {
	@NotNull
    private String carId;
    @NotNull
    private LocalDateTime dateStarted;
    @NotNull
    private int rentedForDays;
    @NotNull
    @Min(0)
    private double dailyPrice;
    @NotNull
    @Min(0)
    private double totalPrice;
}
