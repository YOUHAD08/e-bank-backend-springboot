package ma.youhad.backend.services.implementations;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youhad.backend.entities.*;
import ma.youhad.backend.enums.OperationType;
import ma.youhad.backend.exceptions.BankAccountNotFoundException;
import ma.youhad.backend.exceptions.CustomerNotFoundException;
import ma.youhad.backend.exceptions.InsufficientBalanceException;
import ma.youhad.backend.repositories.AccountOperationRepository;
import ma.youhad.backend.repositories.BankAccountRepository;
import ma.youhad.backend.repositories.CustomerRepository;
import ma.youhad.backend.services.interfaces.BankAccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {

    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    private CustomerRepository customerRepository;
    // this used to log content messages using SLF4J API
    // Logger log = LoggerFactory.getLogger(BankAccountServiceImpl.class);

    @Override
    public CurrentAccount createCurrentBankAccount(double initialBalance, Long customerId ,double overDraft) throws CustomerNotFoundException {
        // Code : Service Rules +
        CurrentAccount currentAccount = new CurrentAccount();
        Customer customer = customerRepository.findById(customerId).orElse(null) ;
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found");
        }
        else {
            currentAccount.setId(UUID.randomUUID().toString());
            currentAccount.setBalance(initialBalance);
            currentAccount.setCreatedAt(new Date());
            currentAccount.setCustomer(customer);
            currentAccount.setOverdraft(overDraft);
            return bankAccountRepository.save(currentAccount);
        }

    }

    @Override
    public SavingAccount createSavingBankAccount(double initialBalance, Long customerId, double interestRate) throws CustomerNotFoundException {
        // Code : Service Rules +
        SavingAccount savingAccount = new SavingAccount();
        Customer customer = customerRepository.findById(customerId).orElse(null) ;
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found");
        }
        else {
            savingAccount.setId(UUID.randomUUID().toString());
            savingAccount.setBalance(initialBalance);
            savingAccount.setCreatedAt(new Date());
            savingAccount.setCustomer(customer);
            savingAccount.setInterestRate(interestRate);
            return bankAccountRepository.save(savingAccount);
        }
    }

    @Override
    public BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException {
        // Code : Service Rules +
        log.info("getBankAccount called");
        return bankAccountRepository.findById(accountId).orElseThrow(()-> new BankAccountNotFoundException("Bank Account not found"));
    }

    @Override
    public List<BankAccount> getAllBankAccounts() {
        log.info("getAllBankAccounts called");
        return bankAccountRepository.findAll();
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, InsufficientBalanceException {
        log.info("debit called");
        // Code : Service Rules +
        BankAccount bankAccount = getBankAccount(accountId);
        if (bankAccount.getBalance() < amount) {
            throw new InsufficientBalanceException("Not enough balance");
        }
        // Save the operation
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        // Save the changes
        bankAccount.setBalance(bankAccount.getBalance() - amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
        log.info("credit called");
        BankAccount bankAccount = getBankAccount(accountId);
        // Save the operation
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        // Save the changes
        bankAccount.setBalance(bankAccount.getBalance() + amount);
        bankAccountRepository.save(bankAccount);

    }

    @Override
    public void transfer(String fromCustomerId, String toCustomerId, double amount) throws BankAccountNotFoundException, InsufficientBalanceException {
        log.info("transfer called");
        debit(fromCustomerId, amount, "Transfer to" + toCustomerId);
        credit(toCustomerId, amount, "Transfer from" + fromCustomerId);

    }
}
