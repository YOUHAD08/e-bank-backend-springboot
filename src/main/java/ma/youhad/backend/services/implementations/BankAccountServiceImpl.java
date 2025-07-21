package ma.youhad.backend.services.implementations;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youhad.backend.dtos.BankAccountDTO;
import ma.youhad.backend.dtos.CurrentAccountDTO;
import ma.youhad.backend.dtos.CustomerDTO;
import ma.youhad.backend.dtos.SavingAccountDTO;
import ma.youhad.backend.entities.*;
import ma.youhad.backend.exceptions.BankAccountNotFoundException;
import ma.youhad.backend.exceptions.CustomerNotFoundException;
import ma.youhad.backend.mappers.CurrentAccountMapperImpl;
import ma.youhad.backend.mappers.CustomerMapperImpl;
import ma.youhad.backend.mappers.SavingAccountMapperImpl;
import ma.youhad.backend.repositories.AccountOperationRepository;
import ma.youhad.backend.repositories.BankAccountRepository;
import ma.youhad.backend.repositories.CustomerRepository;
import ma.youhad.backend.services.interfaces.BankAccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {

    private final CustomerMapperImpl customerMapperImpl;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    private CustomerRepository customerRepository;
    private CurrentAccountMapperImpl currentAccountMapper;
    private SavingAccountMapperImpl savingAccountMapper;
    // this used to log content messages using SLF4J API
    // Logger log = LoggerFactory.getLogger(BankAccountServiceImpl.class);

    @Override
    public CurrentAccountDTO createCurrentBankAccount(CurrentAccountDTO currentAccountDTO, long customerId) throws CustomerNotFoundException {
        // Code : Service Rules +
        Customer customer = customerRepository.findById(customerId).orElse(null) ;
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found");
        }
        else {
            CustomerDTO customerDTO = customerMapperImpl.fromCustomerToCustomerDTO(customer);
            currentAccountDTO.setCustomerDTO(customerDTO);
            currentAccountDTO.setId(UUID.randomUUID().toString());
            CurrentAccount currentAccount = currentAccountMapper.fromCurrentAccountDTOToCurrentAccount(currentAccountDTO);
            return currentAccountMapper.fromCurrentAccountToCurrentAccountDTO(bankAccountRepository.save(currentAccount));
        }

    }

    @Override
    public SavingAccountDTO createSavingBankAccount(SavingAccountDTO savingAccountDTO, long customerId) throws CustomerNotFoundException {
        // Code : Service Rules +
        Customer customer = customerRepository.findById(customerId).orElse(null) ;
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found");
        }
        else {
            CustomerDTO customerDTO = customerMapperImpl.fromCustomerToCustomerDTO(customer);
            savingAccountDTO.setCustomerDTO(customerDTO);
            savingAccountDTO.setId(UUID.randomUUID().toString());
            SavingAccount savingAccount = savingAccountMapper.fromSavingAccountDTOToSavingAccount(savingAccountDTO);
            return savingAccountMapper.fromSavingAccountToSavingAccountDTO(bankAccountRepository.save(savingAccount));
        }
    }

    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
        // Code : Service Rules +
        log.info("getBankAccount called");
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(()-> new BankAccountNotFoundException("Bank Account not found"));
        if(bankAccount instanceof SavingAccount savingAccount) {
            return savingAccountMapper.fromSavingAccountToSavingAccountDTO(savingAccount);
        }
        else {
            CurrentAccount currentAccount = (CurrentAccount) bankAccount;
            return currentAccountMapper.fromCurrentAccountToCurrentAccountDTO(currentAccount);
        }

    }

    @Override
    public List<BankAccountDTO> getAllBankAccounts() {
        log.info("getAllBankAccounts called");
        List<BankAccount> bankAccounts =bankAccountRepository.findAll();
        return bankAccounts.stream().map(bankAccount ->{
            if(bankAccount instanceof SavingAccount) {
                return savingAccountMapper.fromSavingAccountToSavingAccountDTO((SavingAccount) bankAccount);
            }
            else {
                return currentAccountMapper.fromCurrentAccountToCurrentAccountDTO((CurrentAccount) bankAccount);
            }
        }).collect(Collectors.toList());
    }

    @Override
    public CurrentAccountDTO updateCurrentAccount(String bankAccountId, CurrentAccountDTO currentAccountDTO) {
        log.info("Updating Current Bank Account with id {}", bankAccountId);
        CurrentAccount currentAccount= currentAccountMapper.fromCurrentAccountDTOToCurrentAccount(currentAccountDTO);
        bankAccountRepository.save(currentAccount);
        return currentAccountMapper.fromCurrentAccountToCurrentAccountDTO(currentAccount);

    }

    @Override
    public SavingAccountDTO updateSavingAccount(String bankAccountId, SavingAccountDTO savingAccountDTO) {
        log.info("Updating Saving Bank Account with id {}", bankAccountId);
        SavingAccount savingAccount= savingAccountMapper.fromSavingAccountDTOToSavingAccount(savingAccountDTO);
        bankAccountRepository.save(savingAccount);
        return savingAccountMapper.fromSavingAccountToSavingAccountDTO(savingAccount);

    }

    @Override
    public void deleteBankAccount(String accountId) {
        log.info("Delete Bank Account with id {}", accountId);
        bankAccountRepository.deleteById(accountId);
    }

    @Override
    public List<BankAccountDTO> getAllBankAccountsbyCustomer(long customerId) {
        log.info("get all Bank Accounts by customer id {}", customerId);
        List<BankAccount> bankAccounts =bankAccountRepository.findByCustomerId(customerId);
        return bankAccounts.stream().map(bankAccount ->{
            if(bankAccount instanceof SavingAccount) {
                return savingAccountMapper.fromSavingAccountToSavingAccountDTO((SavingAccount) bankAccount);
            }
            else {
                return currentAccountMapper.fromCurrentAccountToCurrentAccountDTO((CurrentAccount) bankAccount);
            }
        }).collect(Collectors.toList());
    }
}
