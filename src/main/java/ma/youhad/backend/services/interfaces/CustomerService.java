package ma.youhad.backend.services.interfaces;

import ma.youhad.backend.dtos.CustomerDTO;
import ma.youhad.backend.entities.Customer;
import ma.youhad.backend.exceptions.CustomerNotFoundException;

import java.util.List;

public interface CustomerService {
    CustomerDTO createCustomer(CustomerDTO customerDTO);
    List<CustomerDTO> getCustomers();
    CustomerDTO getCustomer(long id) throws CustomerNotFoundException;
    CustomerDTO updateCustomer(long id, CustomerDTO customerDTO);
    void deleteCustomer(long id);
}
