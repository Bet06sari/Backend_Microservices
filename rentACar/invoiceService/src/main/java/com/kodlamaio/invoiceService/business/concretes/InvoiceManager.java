package com.kodlamaio.invoiceService.business.concretes;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.invoiceService.business.abstracts.InvoiceService;
import com.kodlamaio.invoiceService.business.responses.GetAllInvoicesResponse;
import com.kodlamaio.invoiceService.dataAccess.InvoiceRepository;
import com.kodlamaio.invoiceService.entities.Invoice;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InvoiceManager implements InvoiceService{

	private InvoiceRepository invoiceRepository;
	private ModelMapperService mapperService;
	
	@Override
    public List<GetAllInvoicesResponse> getAll() {
        List<Invoice> invoiceList = invoiceRepository.findAll();
        List<GetAllInvoicesResponse> response = invoiceList.stream()
                .map(invoice -> mapperService.forResponse().map(invoice,GetAllInvoicesResponse.class))
                .collect(Collectors.toList());
        return response;
    }

	@Override
    public void add(Invoice invoice) {
        invoice.setId(UUID.randomUUID().toString());
        invoiceRepository.save(invoice);
    }
}
