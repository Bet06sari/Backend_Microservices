package com.kodlamaio.invoiceService.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "invoices")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {
	@Id
	@Column(name="id")
	private String id;
	@Column(name="fullName")
	private String fullName;
	@Column(name="brandName")
    private String brandName;
	@Column(name="modelName")
    private String modelName;
	@Column(name="dailyPrice")
    private double dailyPrice;
	@Column(name="rentedForDays")
    private int rentedForDays;
	@Column(name="totalPrice")
	private double totalPrice;
}
