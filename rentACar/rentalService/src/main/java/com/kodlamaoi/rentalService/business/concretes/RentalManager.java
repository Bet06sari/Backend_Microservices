package com.kodlamaoi.rentalService.business.concretes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.RentalCreatedEvent;
import com.kodlamaio.common.events.RentalUpdatedCarEvent;
import com.kodlamaio.common.requests.CreatePaymentRequest;
import com.kodlamaio.common.utilities.exceptions.BusinessException;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaoi.rentalService.business.abstracts.RentalService;
import com.kodlamaoi.rentalService.business.request.CreateRentalRequest;
import com.kodlamaoi.rentalService.business.request.UpdateRentalRequest;
import com.kodlamaoi.rentalService.business.response.CreateRentalResponse;
import com.kodlamaoi.rentalService.business.response.GetAllRentalResponse;
import com.kodlamaoi.rentalService.business.response.UpdateRentalResponse;
import com.kodlamaoi.rentalService.client.CarClient;
import com.kodlamaoi.rentalService.client.PaymentClient;
import com.kodlamaoi.rentalService.dataAccess.abstracts.RentalRepository;
import com.kodlamaoi.rentalService.entity.Rental;
import com.kodlamaoi.rentalService.kafka.RentalProducer;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RentalManager implements RentalService{

	private ModelMapperService modelMapperService;
	private RentalRepository rentalRepository;
	private RentalProducer rentalProducer;
	private CarClient carClient;
	private PaymentClient paymentClient;
	
	@Override
	public CreateRentalResponse add(CreateRentalRequest createRentalRequest, CreatePaymentRequest paymentRequest) {
		carClient.checkIfCarAvailable(createRentalRequest.getCarId());
		Rental rental=this.modelMapperService.forRequest().map(createRentalRequest, Rental.class);
		rental.setId(UUID.randomUUID().toString());
		rental.setDateStarted(LocalDateTime.now());
		double totalRentalPrice = rental.getDailyPrice()*rental.getRentedForDays();
		rental.setTotalPrice(totalRentalPrice);
		paymentClient.checkIfPaymentSuccessful(paymentRequest.getCardNumber(),
                paymentRequest.getFullName(), paymentRequest.getCardCvv(),totalRentalPrice);
		this.rentalRepository.save(rental);
		// burda oluşturduğumuz kiralama işlemi messajı inventory e gönderiyoruz.. confuser->database ekledi, producer a bilgi gidiyor, 
		//oda consumer a gönderiyor, inventory e gönderilir. o araç kiralandığı bilgisi oraya gider
		
		RentalCreatedEvent rentalCreatedEvent = new RentalCreatedEvent();
		rentalCreatedEvent.setCarId(createRentalRequest.getCarId());
		rentalCreatedEvent.setMessage("Rental Created");
		
		CreateRentalResponse response = this.modelMapperService.forResponse().map(rental, CreateRentalResponse.class);
		return response;
	}
	@Override
	public List<GetAllRentalResponse> getAll() {
		List<Rental> rentals = this.rentalRepository.findAll();
		List<GetAllRentalResponse> response = rentals.stream().map(rental->this.modelMapperService.forResponse().map(rental, GetAllRentalResponse.class)).collect(Collectors.toList());
		return response;
	}
	@Override
	public UpdateRentalResponse update(UpdateRentalRequest updateRentalRequest) {
		//checkIfRentalNotExistById(updateRentalRequest.getCarId());
		Rental rentalOld = rentalRepository.getRentalById(updateRentalRequest.getCarId()); //eski aracın id sini aldım.
		Rental rental = this.modelMapperService.forRequest().map(updateRentalRequest, Rental.class); //obje oluştu rental adında
		this.rentalRepository.save(rental); // yeni alacağım araç kaydoldu
		
		RentalUpdatedCarEvent rentalUpdatedCarEvent = new RentalUpdatedCarEvent();
		rentalUpdatedCarEvent.setOldCarId(rentalOld.getCarId());
		rentalUpdatedCarEvent.setNewCarId(rental.getCarId()); 
		
		rentalProducer.sendMessage(rentalUpdatedCarEvent);
		return modelMapperService.forResponse().map(rental, UpdateRentalResponse.class);
		
	}
	@Override
	public void delete(String id) {
		//checkIfRentalNotExistById(id);
		this.rentalRepository.findById(id);
		throw new BusinessException("calisti");
		
	}	
	private void checkIfRentalNotExistById (String id) {
		Rental currentRental = this.rentalRepository.findById(id);
		if(currentRental == null)
			throw new BusinessException("Rental not found");
	}
		
}
