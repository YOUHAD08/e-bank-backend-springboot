package ma.youhad.backend.services.interfaces;

import ma.youhad.backend.dtos.AccountOperationDTO;

import java.util.List;

public interface AccountOperationService {
    List<AccountOperationDTO> accountHistory(String accountId);
}
