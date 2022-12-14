package com.kodlamaio.inventoryService.business.requests.create;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCarRequest {
	@Min(value = 0)
    private double dailyPrice;
    @Min(value = 2015)
    private int modelYear;
    @NotBlank
    @NotNull
    private String plate;
    @NotBlank
    @NotNull
    private String modelId;
    @NotBlank
    @NotNull
    private int state;
	
}
