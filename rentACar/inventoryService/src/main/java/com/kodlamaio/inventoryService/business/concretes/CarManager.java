package com.kodlamaio.inventoryService.business.concretes;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.CarCreatedEvent;
import com.kodlamaio.common.utilities.exceptions.BusinessException;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.inventoryService.business.abstracts.CarService;
import com.kodlamaio.inventoryService.business.requests.create.CreateCarRequest;
import com.kodlamaio.inventoryService.business.requests.update.UpdateCarRequest;
import com.kodlamaio.inventoryService.business.responses.create.CreateCarResponse;
import com.kodlamaio.inventoryService.business.responses.get.GetAllCarResponse;
import com.kodlamaio.inventoryService.business.responses.update.UpdateCarResponse;
import com.kodlamaio.inventoryService.dataAccess.CarRepository;
import com.kodlamaio.inventoryService.entities.Car;
import com.kodlamaio.inventoryService.kafka.FilterServiceProducer;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CarManager   implements CarService{
	
	private final CarRepository carRepository;
	private final ModelMapperService modelMapperService;
	private FilterServiceProducer filterServiceProducer;
	
	
	@Override
	public List<GetAllCarResponse> getAll() {

		List<Car> cars = this.carRepository.findAll();
		List<GetAllCarResponse> response = cars.stream()
				.map(car -> this.modelMapperService.forResponse().map(car, GetAllCarResponse.class))
				.collect(Collectors.toList());

		return response;
	}

	@Override
	public CreateCarResponse add(CreateCarRequest createCarRequest) {
		Car car = modelMapperService.forRequest().map(createCarRequest, Car.class);
        car.setId(UUID.randomUUID().toString());
        carRepository.save(car);
        CreateCarResponse response = modelMapperService.forResponse().map(car, CreateCarResponse.class);
        addToFilterService(response.getId());
        return response;
	}

	@Override
	public void delete(String id) {
		checkIfCarExistById(id);
		this.carRepository.findById(id);

	}
	
	@Override
	public void changeCarState(String id) {
		Car car = carRepository.findById(id);
		car.setId("3");
		carRepository.save(car);
	}

	@Override
	public void changeCarState(String oldCarId, String newCarId) {
		Car oldCar = carRepository.findById(oldCarId);
		Car newCar = carRepository.findById(newCarId);
		oldCar.setId(oldCar.getId());
		newCar.setId(newCar.getId());
		
		carRepository.save(oldCar);
		carRepository.save(newCar);
		
	}

	@Override
	public UpdateCarResponse update(UpdateCarRequest updateCarRequest) {
		checkIfCarExistById(updateCarRequest.getId());
		Car car = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);
		this.carRepository.save(car);
		UpdateCarResponse response = this.modelMapperService.forResponse().map(car, UpdateCarResponse.class);
		return response;
	}

	private void checkIfCarExistById(String id) {
		if (carRepository.findById(id) == null)
			throw new BusinessException("BRAND.EXISTS");
	}

	@Override
	public void checkIfCarAvailable(String id) {
		Car car = carRepository.findById(id);
		if (car==null || car.getState() == 3) {
            throw new BusinessException("CAR NOT_AVAILABLE");
		}
	}
	
	@Override
	public GetAllCarResponse getById(String carId) {
		Car car = carRepository.findById(carId);
		GetAllCarResponse response = modelMapperService.forResponse().map(car, GetAllCarResponse.class);
		return response;
	}
	
	private void addToFilterService(String id) {
        Car car = carRepository.findById(id);
        CarCreatedEvent event = modelMapperService.forResponse().map(car,CarCreatedEvent.class);
        event.setMessage("car added");
        filterServiceProducer.sendMessage(event);
    }
	
	
}
