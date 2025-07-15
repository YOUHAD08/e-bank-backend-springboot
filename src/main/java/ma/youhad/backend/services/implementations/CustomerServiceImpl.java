package ma.youhad.backend.services.implementations;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youhad.backend.entities.Customer;
import ma.youhad.backend.repositories.CustomerRepository;
import ma.youhad.backend.services.interfaces.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;
    // this used to log content messages using SLF4J API
    // Logger log = LoggerFactory.getLogger(BankAccountServiceImpl.class);
    @Override
    public Customer createCustomer(Customer customer) {
        log.info("Creating new customer");
        // Code : Service Rules +
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getCustomers() {
        // Code : Service Rules +
        log.info("Getting all customers");
        return customerRepository.findAll();
    }
}
