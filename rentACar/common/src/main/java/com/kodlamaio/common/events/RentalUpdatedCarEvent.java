package com.kodlamaio.common.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalUpdatedCarEvent {
	private String oldCarId;
	private String newCarId;
	private String message;
}
