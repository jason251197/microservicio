package project.jason.store.shoppingservice.serviceImpl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.jason.store.shoppingservice.model.Invoice;
import project.jason.store.shoppingservice.repository.InvoiceItemRepository;
import project.jason.store.shoppingservice.repository.InvoiceRepository;
import project.jason.store.shoppingservice.service.InvoiceService;

import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceItemRepository invoiceItemRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Override
    public List<Invoice> allInvoice() {
        var invoices = this.invoiceRepository.findAll();
        return invoices;
    }

    @Override
    public Invoice getInvoiceById(Long id) {
        var invoice = this.invoiceRepository.findById(id).orElse(null);
        return invoice;
    }

    @Override
    public Invoice createInvoice(Invoice invoice) {
        var findInvoice = this.invoiceRepository.findByNumberInvoice(invoice.getNumberInvoice());
        if (findInvoice != null){
            return findInvoice;
        }
        invoice.setStatus("CREATED");
        return this.invoiceRepository.save(invoice);
    }

    @Override
    public Invoice updateInvoice(Invoice invoice) {
        var findInvoice = this.getInvoiceById(invoice.getId());
        if (findInvoice == null){
            return null;
        }
        findInvoice.setCustomerId(invoice.getCustomerId());
        findInvoice.setDescription(invoice.getDescription());
        findInvoice.setNumberInvoice(invoice.getNumberInvoice());
        findInvoice.getItems().clear();
        findInvoice.setItems(invoice.getItems());
        return this.invoiceRepository.save(findInvoice);
    }

    @Override
    public Invoice deleteInvoice(Invoice invoice) {
        var findInvoice = this.getInvoiceById(invoice.getId());
        if (findInvoice == null){
            return null;
        }
        invoice.setStatus("DELETED");
        return this.invoiceRepository.save(invoice);
    }
}
