package ma.youhad.backend.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youhad.backend.entities.Customer;
import ma.youhad.backend.services.interfaces.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class CustomerRestController {
    CustomerService customerService;

    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        log.info("Get all customers");
        return customerService.getCustomers();
    }
}
