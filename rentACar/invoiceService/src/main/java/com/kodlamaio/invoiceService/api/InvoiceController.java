package com.kodlamaio.invoiceService.api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.invoiceService.business.abstracts.InvoiceService;
import com.kodlamaio.invoiceService.business.responses.GetAllInvoicesResponse;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@AllArgsConstructor
public class InvoiceController {
	private InvoiceService invoiceService;

    @GetMapping
    public List<GetAllInvoicesResponse> getAll(){
        return invoiceService.getAll();
    }

}
