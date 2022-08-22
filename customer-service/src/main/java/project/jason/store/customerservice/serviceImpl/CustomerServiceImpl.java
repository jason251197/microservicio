package project.jason.store.customerservice.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.jason.store.customerservice.model.Customer;
import project.jason.store.customerservice.model.Region;
import project.jason.store.customerservice.repository.CustomerRepository;
import project.jason.store.customerservice.service.CustomerService;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customRepository;

    @Override
    public List<Customer> allCustomer() {
        return this.customRepository.findAll();
    }

    @Override
    public List<Customer> allCustomerByRegion(Region region) {
        return this.customRepository.findByRegion(region);
    }

    @Override
    public Customer createCustomer(Customer customer) {
        Customer findCustomer = this.customRepository.findByNumberID(customer.getNumberID());
        if (findCustomer != null){
            return findCustomer;
        }
        customer.setState("CREATED");
        findCustomer = this.customRepository.save(customer);
        return findCustomer;
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        var findCustomer = this.getCustomer(customer.getId());
        if (findCustomer == null){
            return null;
        }
        findCustomer.setFirstName(customer.getFirstName());
        findCustomer.setLastName(customer.getLastName());
        findCustomer.setEmail(customer.getEmail());
        customer.setPhotoUrl(customer.getPhotoUrl());
        return  this.customRepository.save(customer);
    }

    @Override
    public Customer getCustomer(Long id) {
        var findCustomer = this.customRepository.findById(id).orElse(null);
        return findCustomer;
    }

    @Override
    public Customer deleteCustomer(Customer customer) {
        var findCustomer = this.getCustomer(customer.getId());
        if (findCustomer == null){
            return null;
        }
        customer.setState("DELETED");
        return this.customRepository.save(customer);
    }
}
