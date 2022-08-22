package project.jason.store.customerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.jason.store.customerservice.model.Customer;
import project.jason.store.customerservice.model.Region;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {


    Customer findByNumberID(String numberID);
    List<Customer> findByLastName(String lastName);
    List<Customer> findByRegion(Region region);
}
