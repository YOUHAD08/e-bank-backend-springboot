package ma.youhad.backend.services.interfaces;

import ma.youhad.backend.dtos.AccountHistoryDTO;
import ma.youhad.backend.dtos.AccountOperationDTO;
import ma.youhad.backend.exceptions.BankAccountNotFoundException;

import java.util.List;

public interface AccountOperationService {
    List<AccountOperationDTO> accountHistory(String accountId);
    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;
}
