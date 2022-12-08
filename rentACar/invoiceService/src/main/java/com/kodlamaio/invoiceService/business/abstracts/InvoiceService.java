package com.kodlamaio.invoiceService.business.abstracts;
import java.util.List;

import com.kodlamaio.invoiceService.business.responses.GetAllInvoicesResponse;
import com.kodlamaio.invoiceService.entities.Invoice;

public interface InvoiceService {
	List<GetAllInvoicesResponse> getAll();
	void add(Invoice invoice);

}
