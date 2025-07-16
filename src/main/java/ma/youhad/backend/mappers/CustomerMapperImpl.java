package ma.youhad.backend.mappers;

import ma.youhad.backend.dtos.CustomerDTO;
import ma.youhad.backend.entities.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;



// MapStruct is framework that does the mapping from entities to DTOS and vis versa
// in this framework we only define interfaces and method signatures and MapStruct
// will generate th code
@Service
public class CustomerMapperImpl {

    public CustomerDTO fromCustomerToCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        return customerDTO;
    }

    public Customer fromCustomerDTOToCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }
}
