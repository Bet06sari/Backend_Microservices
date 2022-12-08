package com.kodlamaoi.rentalService.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.common.requests.CreatePaymentRequest;
import com.kodlamaoi.rentalService.business.abstracts.RentalService;
import com.kodlamaoi.rentalService.business.request.CreateRentalRequest;
import com.kodlamaoi.rentalService.business.request.UpdateRentalRequest;
import com.kodlamaoi.rentalService.business.response.CreateRentalResponse;
import com.kodlamaoi.rentalService.business.response.GetAllRentalResponse;
import com.kodlamaoi.rentalService.business.response.UpdateRentalResponse;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/rentals")
@AllArgsConstructor
public class RentalController {
	private RentalService rentalService;

	@PostMapping
	public CreateRentalResponse add(@Valid @RequestBody CreateRentalRequest createRentalRequest,
			@RequestParam String cardNumber, @RequestParam String fullName, @RequestParam String cardCvv) {
		CreatePaymentRequest paymentRequest = new CreatePaymentRequest();
		paymentRequest.setCardNumber(cardNumber);
		paymentRequest.setFullName(fullName);
		paymentRequest.setCardCvv(cardCvv);
		return rentalService.add(createRentalRequest, paymentRequest);
	}

	@GetMapping()
	public List<GetAllRentalResponse> getAll() {
		return this.rentalService.getAll();
	}

	@PutMapping("/{id}")
    public UpdateRentalResponse update(@PathVariable String id,@RequestBody UpdateRentalRequest updateRentalRequest){
        return rentalService.update(id,updateRentalRequest);
    }

	@DeleteMapping("/{id}")
	public void delete(@PathVariable String id) {
		this.rentalService.delete(id);
	}

}
