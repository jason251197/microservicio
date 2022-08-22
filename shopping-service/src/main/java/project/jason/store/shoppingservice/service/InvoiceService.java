package project.jason.store.shoppingservice.service;

import project.jason.store.shoppingservice.model.Invoice;

import java.util.List;

public interface InvoiceService {
    List<Invoice> allInvoice();
    Invoice getInvoiceById(Long id);
    Invoice createInvoice(Invoice invoice);
    Invoice updateInvoice(Invoice invoice);
    Invoice deleteInvoice(Invoice invoice);
}
