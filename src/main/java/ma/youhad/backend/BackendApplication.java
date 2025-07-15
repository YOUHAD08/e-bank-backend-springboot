package ma.youhad.backend;

import ma.youhad.backend.dtos.BankAccountDTO;
import ma.youhad.backend.dtos.CurrentAccountDTO;
import ma.youhad.backend.dtos.CustomerDTO;
import ma.youhad.backend.dtos.SavingAccountDTO;
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
					CurrentAccountDTO currentAccountDTO = new CurrentAccountDTO();
					currentAccountDTO.setBalance(Math.random() * 10000);
					currentAccountDTO.setOverdraft(1000);
                    bankAccountService.createCurrentBankAccount(currentAccountDTO,customer.getId());

					SavingAccountDTO savingAccountDTO = new SavingAccountDTO();
					savingAccountDTO.setBalance(Math.random() * 10000);
					savingAccountDTO.setInterestRate(5.5);
					bankAccountService.createSavingBankAccount(savingAccountDTO, customer.getId());

					List<BankAccountDTO> bankAccountDTOList =bankAccountService.getAllBankAccounts();
					for (BankAccountDTO bankAccountDTO : bankAccountDTOList) {
						for (int i = 0; i <10 ; i++){
							bankAccountService.credit(bankAccountDTO.getId(),
									10000+Math.random()*12000,
									"Credit");
							bankAccountService.debit(bankAccountDTO.getId(),
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
