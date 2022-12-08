package com.kodlamaio.inventoryService.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodlamaio.inventoryService.entities.Car;

public interface CarRepository extends JpaRepository<Car, Integer>{
	Car findById(String id);
	Car findByState(int id);
	void setCarStateById(String id,int state);
}
