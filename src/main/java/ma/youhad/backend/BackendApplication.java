package ma.youhad.backend;

import ma.youhad.backend.dtos.BankAccountDTO;
import ma.youhad.backend.dtos.CurrentAccountDTO;
import ma.youhad.backend.dtos.CustomerDTO;
import ma.youhad.backend.dtos.SavingAccountDTO;
import ma.youhad.backend.enums.AccountStatus;
import ma.youhad.backend.exceptions.CustomerNotFoundException;
import ma.youhad.backend.services.interfaces.AccountOperationService;
import ma.youhad.backend.services.interfaces.BankAccountService;
import ma.youhad.backend.services.interfaces.CustomerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

@SpringBootApplication
public class BackendApplication {


	private final AccountOperationService accountOperationService;
	long startMillis = new Date(120, 0, 1).getTime(); // January 1, 2020
	long endMillis = System.currentTimeMillis();      // current date/time
	public BackendApplication(AccountOperationService accountOperationService) {
        this.accountOperationService = accountOperationService;
    }

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
				long randomMillis = ThreadLocalRandom.current().nextLong(startMillis, endMillis);
				Date randomDate = new Date(randomMillis);
                try {
					CurrentAccountDTO currentAccountDTO = new CurrentAccountDTO();
					currentAccountDTO.setBalance(Math.random() * 10000);
					currentAccountDTO.setOverdraft(1000);
					currentAccountDTO.setCreatedAt(randomDate);
					currentAccountDTO.setStatus(AccountStatus.ACTIVATED);
                    bankAccountService.createCurrentBankAccount(currentAccountDTO,customer.getId());

					CurrentAccountDTO currentAccountDTO2 = new CurrentAccountDTO();
					currentAccountDTO2.setBalance(Math.random() * 10000);
					currentAccountDTO2.setOverdraft(1000);
					currentAccountDTO2.setCreatedAt(randomDate);
					currentAccountDTO2.setStatus(AccountStatus.SUSPENDED);
					bankAccountService.createCurrentBankAccount(currentAccountDTO2,customer.getId());

					SavingAccountDTO savingAccountDTO = new SavingAccountDTO();
					savingAccountDTO.setBalance(Math.random() * 10000);
					savingAccountDTO.setInterestRate(5.5);
					savingAccountDTO.setCreatedAt(randomDate);
					savingAccountDTO.setStatus(AccountStatus.CREATED);
					bankAccountService.createSavingBankAccount(savingAccountDTO, customer.getId());

					SavingAccountDTO savingAccountDTO2 = new SavingAccountDTO();
					savingAccountDTO2.setBalance(Math.random() * 10000);
					savingAccountDTO2.setInterestRate(5.5);
					savingAccountDTO2.setCreatedAt(randomDate);
					savingAccountDTO2.setStatus(AccountStatus.ACTIVATED);
					bankAccountService.createSavingBankAccount(savingAccountDTO2, customer.getId());
                } catch (CustomerNotFoundException e) {
                    e.printStackTrace();
                }
            });
			List<BankAccountDTO> bankAccounts = bankAccountService.getAllBankAccounts();
			for (BankAccountDTO bankAccount:bankAccounts){
				for (int i = 0; i <10 ; i++) {
					String accountId;
					if(bankAccount instanceof SavingAccountDTO){
						accountId=((SavingAccountDTO) bankAccount).getId();
					} else{
						accountId=((CurrentAccountDTO) bankAccount).getId();
					}
					accountOperationService.credit(accountId,10000+Math.random()*120000,"Credit");
					accountOperationService.debit(accountId,1000+Math.random()*9000,"Debit");
				}
			}
			};
	}
}
