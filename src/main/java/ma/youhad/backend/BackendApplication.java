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


import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

@SpringBootApplication
public class BackendApplication {


	private final AccountOperationService accountOperationService;
	long startMillis = new Date(120, 0, 1).getTime(); // January 1, 2020
	long endMillis = System.currentTimeMillis();
	List<String> names = List.of("Ayoub", "Yasmine", "Khalid", "Fatima", "Mohammed", "Sara", "Rachid", "Laila", "Omar", "Nada");// current date/time
	List<String> descriptions = List.of(
			"Salary", "Invoice payment", "Loan refund", "Online shopping", "Electric bill", "Water bill", "Car repair", "Grocery", "Bonus", "Refund"
	);
	List<String> cities = List.of(
			"Casablanca", "Rabat", "Fes", "Marrakech", "Tangier", "Agadir", "Oujda", "Meknes"
	);
	Random random = new Random();

	public BackendApplication(AccountOperationService accountOperationService) {
        this.accountOperationService = accountOperationService;
    }

    public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
	@Bean
	CommandLineRunner start(BankAccountService bankAccountService, CustomerService customerService) {
		return args -> {
			for (String name : names) {
				CustomerDTO customerDTO = new CustomerDTO();
				customerDTO.setName(name);
				customerDTO.setEmail(name.toLowerCase() + "@gmail.com");
				String randomCity = cities.get(random.nextInt(cities.size()));
				customerDTO.setCity(randomCity);
				customerService.createCustomer(customerDTO);
			}

			customerService.getCustomers().forEach(customer -> {
				int numAccounts = ThreadLocalRandom.current().nextInt(3, 6); // 3 to 5 accounts per customer
				for (int i = 0; i < numAccounts; i++) {
					try {
						long randomMillis = ThreadLocalRandom.current().nextLong(startMillis, endMillis);
						Date randomDate = new Date(randomMillis);
						double balance = 5000 + Math.random() * 15000;

						if (i % 2 == 0) {
							// Create current account
							CurrentAccountDTO currentAccountDTO = new CurrentAccountDTO();
							currentAccountDTO.setBalance(balance);
							currentAccountDTO.setOverdraft(ThreadLocalRandom.current().nextInt(500, 2000));
							currentAccountDTO.setCreatedAt(randomDate);
							currentAccountDTO.setStatus(AccountStatus.values()[ThreadLocalRandom.current().nextInt(AccountStatus.values().length)]);
							bankAccountService.createCurrentBankAccount(currentAccountDTO, customer.getId());
						} else {
							// Create saving account
							SavingAccountDTO savingAccountDTO = new SavingAccountDTO();
							savingAccountDTO.setBalance(balance);
							savingAccountDTO.setInterestRate(2 + Math.random() * 5); // 2% to 7%
							savingAccountDTO.setCreatedAt(randomDate);
							savingAccountDTO.setStatus(AccountStatus.values()[ThreadLocalRandom.current().nextInt(AccountStatus.values().length)]);
							bankAccountService.createSavingBankAccount(savingAccountDTO, customer.getId());
						}

					} catch (CustomerNotFoundException e) {
						e.printStackTrace();
					}
				}
			});

			// Add credit/debit operations
			List<BankAccountDTO> bankAccounts = bankAccountService.getAllBankAccounts();
			for (BankAccountDTO bankAccount : bankAccounts) {
				String accountId = (bankAccount instanceof SavingAccountDTO)
						? ((SavingAccountDTO) bankAccount).getId()
						: ((CurrentAccountDTO) bankAccount).getId();
				Random random = new Random();
				int numOps = ThreadLocalRandom.current().nextInt(10, 21); // 10–20 operations
				for (int i = 0; i < numOps; i++) {
					String description = descriptions.get(random.nextInt(descriptions.size()));
					double creditAmount = 1000 + Math.random() * 10000;
					double debitAmount = 200 + Math.random() * 3000;
					accountOperationService.credit(accountId, creditAmount, description);
					accountOperationService.debit(accountId, debitAmount, description);
				}
			}
		};
	}

}
