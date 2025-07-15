package ma.youhad.backend.services.interfaces;

import ma.youhad.backend.dtos.BankAccountDTO;
import ma.youhad.backend.dtos.CurrentAccountDTO;
import ma.youhad.backend.dtos.SavingAccountDTO;
import ma.youhad.backend.exceptions.BankAccountNotFoundException;
import ma.youhad.backend.exceptions.CustomerNotFoundException;
import ma.youhad.backend.exceptions.InsufficientBalanceException;

import java.util.List;

public interface BankAccountService {
    CurrentAccountDTO createCurrentBankAccount(CurrentAccountDTO currentAccountDTO, long customerId) throws CustomerNotFoundException;
    SavingAccountDTO createSavingBankAccount(SavingAccountDTO savingAccountDTO, long customerId) throws CustomerNotFoundException;
    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
    List<BankAccountDTO> getAllBankAccounts();
    CurrentAccountDTO updateCurrentAccount(String bankAccountId, CurrentAccountDTO currentAccountDTO);
    SavingAccountDTO updateSavingAccount(String bankAccountId, SavingAccountDTO savingAccountDTO);
    void deleteBankAccount(String accountId);
    void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, InsufficientBalanceException;
    void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;
    void transfer(String fromCustomerId, String toCustomerId, double amount) throws BankAccountNotFoundException, InsufficientBalanceException;

}
