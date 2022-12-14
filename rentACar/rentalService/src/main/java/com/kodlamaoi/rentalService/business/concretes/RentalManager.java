package com.kodlamaoi.rentalService.business.concretes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.InvoiceCreatedEvent;
import com.kodlamaio.common.events.RentalCreatedEvent;
import com.kodlamaio.common.events.RentalUpdatedCarEvent;
import com.kodlamaio.common.requests.CreatePaymentRequest;
import com.kodlamaio.common.responses.GetAllCarResponse;
import com.kodlamaio.common.utilities.exceptions.BusinessException;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaoi.rentalService.business.abstracts.RentalService;
import com.kodlamaoi.rentalService.business.request.CreateRentalRequest;
import com.kodlamaoi.rentalService.business.request.UpdateRentalRequest;
import com.kodlamaoi.rentalService.business.response.CreateRentalResponse;
import com.kodlamaoi.rentalService.business.response.GetAllRentalResponse;
import com.kodlamaoi.rentalService.business.response.UpdateRentalResponse;
import com.kodlamaoi.rentalService.client.CarClient;
import com.kodlamaoi.rentalService.client.InventoryClient;
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
	private InventoryClient inventoryClient;
	
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
		// burda olu??turdu??umuz kiralama i??lemi messaj?? inventory e g??nderiyoruz.. confuser->database ekledi, producer a bilgi gidiyor, 
		//oda consumer a g??nderiyor, inventory e g??nderilir. o ara?? kiraland?????? bilgisi oraya gider
		
		RentalCreatedEvent rentalCreatedEvent = new RentalCreatedEvent();
		rentalCreatedEvent.setCarId(createRentalRequest.getCarId());
		rentalCreatedEvent.setMessage("Rental Created");
		createInvoiceProducer(rental,paymentRequest);
		rentalProducer.sendMessage(rentalCreatedEvent);
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
	public UpdateRentalResponse update(String id, UpdateRentalRequest updateRentalRequest) {
		checkIfRentalNotExistById(updateRentalRequest.getCarId());
		Rental rentalOld = rentalRepository.getRentalById(updateRentalRequest.getCarId()); //eski arac??n id sini ald??m.
		Rental rental = this.modelMapperService.forRequest().map(updateRentalRequest, Rental.class); //obje olu??tu rental ad??nda
		this.rentalRepository.save(rental); // yeni alaca????m ara?? kaydoldu
		
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
	
	private void createInvoiceProducer(Rental rental, CreatePaymentRequest paymentRequest) {
        InvoiceCreatedEvent invoiceCreateEvent = new InvoiceCreatedEvent();
        GetAllCarResponse car = inventoryClient.getCarById(rental.getCarId());
        invoiceCreateEvent.setBrandName(car.getBrandName());
        invoiceCreateEvent.setModelName(car.getModelName());
        invoiceCreateEvent.setTotalPrice(rental.getTotalPrice());
        invoiceCreateEvent.setFullName(paymentRequest.getFullName());
        invoiceCreateEvent.setDailyPrice(rental.getDailyPrice());
        invoiceCreateEvent.setRentedForDays(rental.getRentedForDays());
        //rentalProducer.sendMessage(invoiceCreateEvent);
    }
		
}
