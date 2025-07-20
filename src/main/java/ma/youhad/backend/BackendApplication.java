package ma.youhad.backend;

import ma.youhad.backend.dtos.BankAccountDTO;
import ma.youhad.backend.dtos.CurrentAccountDTO;
import ma.youhad.backend.dtos.CustomerDTO;
import ma.youhad.backend.dtos.SavingAccountDTO;
import ma.youhad.backend.exceptions.CustomerNotFoundException;
import ma.youhad.backend.services.interfaces.AccountOperationService;
import ma.youhad.backend.services.interfaces.BankAccountService;
import ma.youhad.backend.services.interfaces.CustomerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class BackendApplication {


	private final AccountOperationService accountOperationService;

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
                try {
					CurrentAccountDTO currentAccountDTO = new CurrentAccountDTO();
					currentAccountDTO.setBalance(Math.random() * 10000);
					currentAccountDTO.setOverdraft(1000);
                    bankAccountService.createCurrentBankAccount(currentAccountDTO,customer.getId());

					SavingAccountDTO savingAccountDTO = new SavingAccountDTO();
					savingAccountDTO.setBalance(Math.random() * 10000);
					savingAccountDTO.setInterestRate(5.5);
					bankAccountService.createSavingBankAccount(savingAccountDTO, customer.getId());
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
