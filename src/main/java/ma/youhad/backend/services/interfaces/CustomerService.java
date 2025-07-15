package ma.youhad.backend.services.interfaces;

import ma.youhad.backend.entities.Customer;

import java.util.List;

public interface CustomerService {
    Customer createCustomer(Customer customer);
    List<Customer> getCustomers();
}
