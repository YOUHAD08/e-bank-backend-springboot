package ma.youhad.backend.services.implementations;

import ma.youhad.backend.entities.Customer;
import ma.youhad.backend.repositories.CustomerRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    public CustomUserDetailsService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)  {
        Customer customer = customerRepository.findByName(username);

        // Split comma-separated roles
        List<GrantedAuthority> authorities = Arrays.stream(customer.getRoles().split(","))
                .map(String::trim) // remove extra spaces
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                customer.getName(),
                customer.getPassword(),
                authorities
        );
    }
}
