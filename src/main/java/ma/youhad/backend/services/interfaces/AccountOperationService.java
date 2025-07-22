package ma.youhad.backend.services.interfaces;

import ma.youhad.backend.dtos.AccountHistoryDTO;
import ma.youhad.backend.dtos.AccountOperationDTO;
import ma.youhad.backend.exceptions.BankAccountNotFoundException;
import ma.youhad.backend.exceptions.InsufficientBalanceException;

import java.util.List;

public interface AccountOperationService {
    List<AccountOperationDTO> accountHistory(String accountId);
    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;

    void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, InsufficientBalanceException;

    void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;

    void transfer(String fromCustomerId, String toCustomerId, double amount) throws BankAccountNotFoundException, InsufficientBalanceException;

    List<AccountOperationDTO> getAllOperations();
}
