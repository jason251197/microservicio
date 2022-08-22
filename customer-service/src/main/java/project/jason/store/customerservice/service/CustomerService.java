package project.jason.store.customerservice.service;

import project.jason.store.customerservice.model.Customer;
import project.jason.store.customerservice.model.Region;

import java.util.List;

public interface CustomerService {
    List<Customer> allCustomer();
    List<Customer> allCustomerByRegion(Region region);
    Customer createCustomer(Customer customer);

    Customer updateCustomer(Customer customer);

    Customer getCustomer(Long id);

    Customer deleteCustomer(Customer customer);

}
