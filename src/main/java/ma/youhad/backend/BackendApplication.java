package ma.youhad.backend;

import ma.youhad.backend.dtos.CustomerDTO;
import ma.youhad.backend.entities.*;
import ma.youhad.backend.enums.AccountStatus;
import ma.youhad.backend.enums.OperationType;
import ma.youhad.backend.exceptions.BankAccountNotFoundException;
import ma.youhad.backend.exceptions.CustomerNotFoundException;
import ma.youhad.backend.exceptions.InsufficientBalanceException;
import ma.youhad.backend.repositories.AccountOperationRepository;
import ma.youhad.backend.repositories.BankAccountRepository;
import ma.youhad.backend.repositories.CustomerRepository;
import ma.youhad.backend.services.interfaces.BankAccountService;
import ma.youhad.backend.services.interfaces.CustomerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
	@Bean
	CommandLineRunner start(BankAccountService bankAccountService, CustomerService customerService) {
		return args -> {
			Stream.of("Ayoub","Yasmine","Khalid").forEach(name -> {
				CustomerDTO customerDTO = new CustomerDTO();
				customerDTO.setName(name);
				customerDTO.setEmail(name+"@gmail.com");
				customerService.createCustomer(customerDTO);
			});
			customerService.getCustomers().forEach(customer -> {
                try {
                    bankAccountService.createCurrentBankAccount(Math.random() * 10000,
							customer.getId(),
							1000);
					bankAccountService.createSavingBankAccount(Math.random() * 10000,
							customer.getId(),
							5.5);
					List<BankAccount> bankAccountList =bankAccountService.getAllBankAccounts();
					for (BankAccount bankAccount : bankAccountList) {
						for (int i = 0; i <10 ; i++){
							bankAccountService.credit(bankAccount.getId(),
									10000+Math.random()*12000,
									"Credit");
							bankAccountService.debit(bankAccount.getId(),
									1000+Math.random()*9000,
									"Debit");
						}
					}
                } catch (CustomerNotFoundException | BankAccountNotFoundException | InsufficientBalanceException e) {
                    e.printStackTrace();
                }
            });
			};
	}
}
