package com.kodlamaio.paymentService.adapters;

import com.kodlamaio.common.utilities.exceptions.BusinessException;
import com.kodlamaio.paymentService.businsess.abstracts.PosService;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class FakePosServiceAdaptor implements PosService{

	@Override
	public void pay() {
		int randomNumber = new Random().nextInt(10);
        if (randomNumber < 5) {
            throw new BusinessException("Invalid payment");
        	}
	}
}
