package com.kodlamaio.filterService.business.responses;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllCarFiltersResponse {
	private String id;
    private String carId;
    private String brandId;
    private String brandName;
    private String modelId;
    private String modelName;
    private int modelYear;
    private double dailyPrice;
    private String plate;
    private int state;

}
