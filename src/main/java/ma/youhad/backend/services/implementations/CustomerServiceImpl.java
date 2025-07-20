package ma.youhad.backend.services.implementations;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youhad.backend.dtos.CustomerDTO;
import ma.youhad.backend.entities.Customer;
import ma.youhad.backend.exceptions.CustomerNotFoundException;
import ma.youhad.backend.mappers.CustomerMapperImpl;
import ma.youhad.backend.repositories.CustomerRepository;
import ma.youhad.backend.services.interfaces.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;
    private CustomerMapperImpl customerMapper;
    // this used to log content messages using SLF4J API
    // Logger log = LoggerFactory.getLogger(BankAccountServiceImpl.class);
    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        log.info("Creating new customer");
        // Code : Service Rules +
        Customer customer = customerMapper.fromCustomerDTOToCustomer(customerDTO);
        customerRepository.save(customer);
        return customerMapper.fromCustomerToCustomerDTO(customer);
    }

    @Override
    public List<CustomerDTO> getCustomers() {
        // Code : Service Rules +
        log.info("Getting all customers");
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(customer -> customerMapper.fromCustomerToCustomerDTO(customer)).collect(Collectors.toList());

    }

    @Override
    public CustomerDTO getCustomer(long id) throws CustomerNotFoundException {
        log.info("Getting customer with id {}", id);
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer "+id+" not found"));
        return customerMapper.fromCustomerToCustomerDTO(customer);
    }

    @Override
    public CustomerDTO updateCustomer(long id, CustomerDTO customerDTO) {
        log.info("Updating customer with id {}", id);
        Customer customer = customerMapper.fromCustomerDTOToCustomer(customerDTO);
        customerRepository.save(customer);
        return customerMapper.fromCustomerToCustomerDTO(customer);
    }

    @Override
    public void deleteCustomer(long id) {
        log.info("Deleting customer with id {}", id);
        customerRepository.deleteById(id);
    }

    @Override
    public List<CustomerDTO> searchCustomers(String keyword) {
        List<Customer> customers = customerRepository.searchCustomer(keyword);
        return customers.stream().map(customer -> customerMapper.fromCustomerToCustomerDTO(customer)).collect(Collectors.toList());
    }
}
