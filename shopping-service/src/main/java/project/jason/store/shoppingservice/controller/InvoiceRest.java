package project.jason.store.shoppingservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import project.jason.store.shoppingservice.model.Invoice;
import project.jason.store.shoppingservice.service.InvoiceService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/invoice")
public class InvoiceRest {

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("/all")
    public ResponseEntity<List<Invoice>> allInvoices(){
        var invoices = this.invoiceService.allInvoice();
        if (invoices.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(invoices);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Invoice> findInvoice(@PathVariable Long id){
       log.info("Fetching Invoice with id {}",id);
        var findInvoice = this.invoiceService.getInvoiceById(id);
        if (findInvoice == null){
            log.error("Invoice with id {} not found",id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(findInvoice);
    }

    @PostMapping("/new")
    public ResponseEntity<Invoice> newInvoice(@RequestBody Invoice invoice, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(bindingResult));
        }
        var findInvoice = this.invoiceService.createInvoice(invoice);
        if (findInvoice == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(findInvoice);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Invoice> updateInvoice(@PathVariable Long id, @RequestBody Invoice invoice){
        var findInvoice = this.invoiceService.getInvoiceById(id);
        if (findInvoice == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(findInvoice);
    }

    private String formatMessage(BindingResult bindingResult){
        List<Map<String, String>> errores = bindingResult.getFieldErrors().stream().map(err->{
            Map<String, String> error = new HashMap<>();
            error.put(err.getField(), err.getDefaultMessage());
            return error;
        }).collect(Collectors.toList());
        var errorMensaje = ErrorMensaje.builder().code("01").mensajes(errores).build();
        var maper = new ObjectMapper();
        String jsontring = "";
        try {
            jsontring = maper.writeValueAsString(errorMensaje);
        }catch (JsonProcessingException jpe){
            jpe.printStackTrace();
        }
        return jsontring;
    }
}
