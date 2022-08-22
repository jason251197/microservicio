package project.jason.store.customerservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import project.jason.store.customerservice.model.Customer;
import project.jason.store.customerservice.model.Region;
import project.jason.store.customerservice.service.CustomerService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> allCustomer(@RequestParam(name = "regionId", required = false) Long regionId){
        List<Customer> customers = new ArrayList<>();
        if (regionId == null){
            customers = this.customerService.allCustomer();
            if (customers.isEmpty()){
                return ResponseEntity.noContent().build();
            }
        }else{
            Region region = new Region();
            region.setId(regionId);
            customers = this.customerService.allCustomerByRegion(region);
            if (customers == null){
                log.error("Customers with Region id {} not found",regionId);
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id){
        var findCustomer = this.customerService.getCustomer(id);
        if (findCustomer == null){
            log.error("Customer with id {} not found",id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(findCustomer);
    }

    @PostMapping("/new")
    public ResponseEntity<Customer> newCustomer(@Valid @RequestBody Customer customer, BindingResult result){
        log.info("Creating Customer : {}",customer);
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        Customer newCustomer = this.customerService.createCustomer(customer);
        if (newCustomer == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(newCustomer);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer){
        log.info("Updating Customer with id {}",id);
        var findCustomer = this.customerService.getCustomer(id);
        if (findCustomer == null){
            log.error("Customer with id {} not found",id);
            return ResponseEntity.notFound().build();
        }
        customer.setId(id);
        findCustomer = this.customerService.updateCustomer(customer);
        return ResponseEntity.ok(findCustomer);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable Long id){
        log.info("Fetching & Deleting Customer with id {}",id);
        var findCustomer = this.customerService.getCustomer(id);
        if (findCustomer == null){
            log.error("Unable to delete. Customer with id {} not found",id);
            return ResponseEntity.notFound().build();
        }
        findCustomer = this.customerService.deleteCustomer(findCustomer);
        return ResponseEntity.ok(findCustomer);
    }


    private String formatMessage(BindingResult bindingResult){
        List<Map<String, String>> errores = bindingResult.getFieldErrors().stream()
                .map(err->{
                    Map<String, String> error = new HashMap<>();
                    error.put(err.getField(),err.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());
        var errorMensaje = ErrorMensaje.builder().code("01").mensajes(errores).build();
        var mapper = new ObjectMapper();
        String jsontring = "";
        try{
            jsontring = mapper.writeValueAsString(errorMensaje);
        }catch (JsonProcessingException jpe){
            jpe.printStackTrace();
        }
        return jsontring;
    }
}
