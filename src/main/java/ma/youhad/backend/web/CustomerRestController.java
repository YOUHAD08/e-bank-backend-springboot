package ma.youhad.backend.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youhad.backend.dtos.CustomerDTO;
import ma.youhad.backend.entities.Customer;
import ma.youhad.backend.exceptions.CustomerNotFoundException;
import ma.youhad.backend.services.interfaces.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class CustomerRestController {
    CustomerService customerService;

    @GetMapping("/customers")
    public List<CustomerDTO> getAllCustomers() {
        log.info("Get all customers");
        return customerService.getCustomers();
    }

    @GetMapping("/customer/{id}")
    public CustomerDTO getCustomer(@PathVariable long id) throws CustomerNotFoundException {
        log.info("Get customer by id");
        return customerService.getCustomer(id);
    }
    @PostMapping("/customer")
    public CustomerDTO createCustomer(@RequestBody CustomerDTO customerDTO) {
        log.info("Create customer");
        return customerService.createCustomer(customerDTO);
    }

    @PutMapping("/customer/{id}")
    public CustomerDTO updateCustomer(@RequestBody CustomerDTO customerDTO, @PathVariable long id) {
        log.info("Update customer");
        customerDTO.setId(id);
        return customerService.updateCustomer(id, customerDTO);
    }

    @DeleteMapping("/customer/{id}")
    public void deleteCustomer(@PathVariable long id) {
        log.info("Delete customer");
        customerService.deleteCustomer(id);
    }
}
