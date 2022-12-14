package com.kodlamaoi.rentalService.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodlamaoi.rentalService.entity.Rental;

public interface RentalRepository extends JpaRepository<Rental, Integer>{
	
	Rental findById(String id);
	Rental getRentalById(String id);

}