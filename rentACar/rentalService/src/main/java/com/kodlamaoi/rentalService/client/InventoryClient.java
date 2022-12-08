package com.kodlamaoi.rentalService.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kodlamaio.common.responses.GetAllCarResponse;

import feign.Headers;

@FeignClient(value = "inventoryclient",url = "http://localhost:9010/stock")
public interface InventoryClient {
	@RequestMapping(method = RequestMethod.GET,value = "/api/cars/{carId}")
    @Headers(value = "Content-Type: application/json")
    GetAllCarResponse getCarById(@PathVariable String carId);

}
