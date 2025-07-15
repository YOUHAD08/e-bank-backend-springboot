package ma.youhad.backend.services.interfaces;

import ma.youhad.backend.entities.BankAccount;
import ma.youhad.backend.entities.CurrentAccount;
import ma.youhad.backend.entities.SavingAccount;
import ma.youhad.backend.exceptions.BankAccountNotFoundException;
import ma.youhad.backend.exceptions.CustomerNotFoundException;
import ma.youhad.backend.exceptions.InsufficientBalanceException;

import java.util.List;

public interface BankAccountService {
    CurrentAccount createCurrentBankAccount(double initialBalance, Long customerId, double overDraft) throws CustomerNotFoundException;
    SavingAccount createSavingBankAccount(double initialBalance, Long customerId, double interestRate) throws CustomerNotFoundException;

    BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException;
    List<BankAccount> getAllBankAccounts();
    void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, InsufficientBalanceException;
    void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;
    void transfer(String fromCustomerId, String toCustomerId, double amount) throws BankAccountNotFoundException, InsufficientBalanceException;

}
