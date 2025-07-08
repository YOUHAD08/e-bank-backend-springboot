package ma.youhad.backend;

import ma.youhad.backend.entities.AccountOperation;
import ma.youhad.backend.entities.CurrentAccount;
import ma.youhad.backend.entities.Customer;
import ma.youhad.backend.entities.SavingAccount;
import ma.youhad.backend.enums.AccountStatus;
import ma.youhad.backend.enums.OperationType;
import ma.youhad.backend.repositories.AccountOperationRepository;
import ma.youhad.backend.repositories.BankAccountRepository;
import ma.youhad.backend.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
	@Bean
	CommandLineRunner start(CustomerRepository customerRepository,
							BankAccountRepository bankAccountRepository,
							AccountOperationRepository accountOperationRepository){
		return args -> {
			Stream.of("Ayoub","Yasmine","Khalid").forEach(name -> {
				Customer customer = new Customer();
				customer.setName(name);
				customer.setEmail(name+"@gmail.com");
				customerRepository.save(customer);

			});
			customerRepository.findAll().forEach(customer -> {
				CurrentAccount currentAccount = new CurrentAccount();
				// Generate an ID that depend on system date
				currentAccount.setId(UUID.randomUUID().toString());
				currentAccount.setCustomer(customer);
				currentAccount.setBalance(Math.random()*10000);
				currentAccount.setOverdraft(10000);
				currentAccount.setCreatedAt(new Date());
				currentAccount.setStatus(AccountStatus.CREATED);
				bankAccountRepository.save(currentAccount);

				SavingAccount savingAccount = new SavingAccount();
				savingAccount.setId(UUID.randomUUID().toString());
				savingAccount.setCustomer(customer);
				savingAccount.setBalance(Math.random()*10000);
				savingAccount.setInterestRate(5.5);
				savingAccount.setCreatedAt(new Date());
				savingAccount.setStatus(AccountStatus.CREATED);
				bankAccountRepository.save(savingAccount);
			});
			bankAccountRepository.findAll().forEach(account->{
				for (int i = 0; i <10 ; i++) {
					AccountOperation accountOperation=new AccountOperation();
					accountOperation.setOperationDate(new Date());
					accountOperation.setAmount(Math.random()*12000);
					accountOperation.setType(Math.random()>0.5? OperationType.DEBIT: OperationType.CREDIT);
					accountOperation.setBankAccount(account);
					accountOperationRepository.save(accountOperation);
				}
			});

		};
	}

}
