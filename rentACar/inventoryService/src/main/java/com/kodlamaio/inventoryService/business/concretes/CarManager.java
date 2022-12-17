package com.kodlamaio.inventoryService.business.concretes;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.CarCreatedEvent;
import com.kodlamaio.common.events.CarDeletedEvent;
import com.kodlamaio.common.events.CarUpdateEvent;
import com.kodlamaio.common.responses.GetAllCarResponse;
import com.kodlamaio.common.utilities.exceptions.BusinessException;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.inventoryService.business.abstracts.CarService;
import com.kodlamaio.inventoryService.business.requests.create.CreateCarRequest;
import com.kodlamaio.inventoryService.business.requests.update.UpdateCarRequest;
import com.kodlamaio.inventoryService.business.responses.create.CreateCarResponse;
import com.kodlamaio.inventoryService.business.responses.update.UpdateCarResponse;
import com.kodlamaio.inventoryService.dataAccess.CarRepository;
import com.kodlamaio.inventoryService.entities.Car;
import com.kodlamaio.inventoryService.kafka.FilterServiceProducer;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CarManager implements CarService{
	
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
		checkIfCarExistsByPlate(createCarRequest.getPlate());
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
		CarDeletedEvent event = new CarDeletedEvent();
        event.setCarId(id);
        filterServiceProducer.sendMessage(event);
        carRepository.deleteById(id);

	}
	
	@Override
	public void changeCarState(String id, int state) {
		carRepository.setCarStateById(id,state);
	}


	@Override
	public UpdateCarResponse update(UpdateCarRequest updateCarRequest, String id) {
		Car car = checkIfCarExistById(id);
		Car updatedCar = modelMapperService.forRequest().map(updateCarRequest, Car.class);
        updatedCar.setId(id);
        updatedCar.setState(car.getState());
        carRepository.save(updatedCar);
        UpdateCarResponse response = modelMapperService.forResponse().map(car, UpdateCarResponse.class);
        updateToFilterService(id,updateCarRequest);
        return response;
	}

	private Car checkIfCarExistById(String id) {
		Car car = carRepository.findById(id);
        if (car==null) {
            throw new BusinessException("CAR.NOT_EXISTS");
        }
        return car;
	}


	private Car checkIfCarAvailable(String id) {
		Car car = carRepository.findById(id);
		if (car==null || car.getState() != 1) {
            throw new BusinessException("CAR NOT_AVAILABLE");
		}
		return car;
	}
	
	@Override
	public GetAllCarResponse getById(String carId) {
		checkIfCarExistById(carId);
		Car car = carRepository.findById(carId);
		GetAllCarResponse response = modelMapperService.forResponse().map(car, GetAllCarResponse.class);
        return response;
	}
	
	private void addToFilterService(String id) {
        Car car = carRepository.findById(id);
        CarCreatedEvent event = modelMapperService.forResponse().map(car,CarCreatedEvent.class);
        filterServiceProducer.sendMessage(event);
    }
	
	private void updateToFilterService(String id,UpdateCarRequest updateCarRequest) {
        Car car = carRepository.findById(id);
        car.getModel().setId(updateCarRequest.getModelId());
        car.getModel().getBrand().setId(car.getModel().getBrand().getId());
        car.setState(updateCarRequest.getState());
        car.setPlate(updateCarRequest.getPlate());
        car.setModelYear(updateCarRequest.getModelYear());
        car.setDailyPrice(updateCarRequest.getDailyPrice());
        CarUpdateEvent event = modelMapperService.forResponse().map(car,CarUpdateEvent.class);
        filterServiceProducer.sendMessage(event);
    }

	@Override
	public void carAvialibleState(String carId) {
		checkIfCarAvailable(carId);
	}
	
	private void checkIfCarExistsByPlate(String plate) {
        if (carRepository.existsByPlateIgnoreCase(plate)) {
            throw new BusinessException("CAR.ALREADY_EXISTS");
        }
    }
	
	
}
