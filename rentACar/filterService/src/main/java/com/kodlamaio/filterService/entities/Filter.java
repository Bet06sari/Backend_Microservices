package com.kodlamaio.filterService.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "filter-inventory")
public class Filter {
	@Id
    private String id;
    @Field(name = "carId")
    private String carId;
    @Field(name = "modelId")
    private String modelId;
    @Field(name = "brandId")
    private String brandId;
    @Field(name = "modelName")
    private String modelName;
    @Field(name = "brandName")
    private String brandName;
    @Field(name = "dailyPrice")
    private double dailyPrice;
    @Field(name = "modelYear")
    private int modelYear;
    @Field(name = "plate")
    private String plate;
    @Field(name = "state")
    private int state;

}
