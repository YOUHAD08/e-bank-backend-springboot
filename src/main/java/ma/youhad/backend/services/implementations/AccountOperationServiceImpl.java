package ma.youhad.backend.services.implementations;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youhad.backend.dtos.AccountHistoryDTO;
import ma.youhad.backend.dtos.AccountOperationDTO;
import ma.youhad.backend.entities.AccountOperation;
import ma.youhad.backend.entities.BankAccount;
import ma.youhad.backend.exceptions.BankAccountNotFoundException;
import ma.youhad.backend.mappers.AccountOperationMapperImp;
import ma.youhad.backend.repositories.AccountOperationRepository;
import ma.youhad.backend.repositories.BankAccountRepository;
import ma.youhad.backend.services.interfaces.AccountOperationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class AccountOperationServiceImpl implements AccountOperationService {

    private final BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    private AccountOperationMapperImp accountOperationMapperImp;

    @Override
    public List<AccountOperationDTO> accountHistory(String accountId){
        List<AccountOperation> accountOperations =accountOperationRepository.findByBankAccountId(accountId);
        return  accountOperations.stream().map(operation -> accountOperationMapperImp.
                fromAccountOperationToAccountOperationDTO(operation)).
                collect(Collectors.toList());
    }

    @Override
    public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException {

        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElse(null);
        if(bankAccount == null) throw new BankAccountNotFoundException("Account Not Found");

        Page<AccountOperation> accountOperations = accountOperationRepository.findByBankAccountId(accountId, PageRequest.of(page, size));
        AccountHistoryDTO accountHistoryDTO = new AccountHistoryDTO();
        List<AccountOperationDTO> accountOperationDTOS = accountOperations.
                getContent().
                stream().
                map(op->accountOperationMapperImp.fromAccountOperationToAccountOperationDTO(op)).collect(Collectors.toList());

        accountHistoryDTO.setAccountOperationDTOs(accountOperationDTOS);
        accountHistoryDTO.setAccountId(bankAccount.getId());
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());
        return accountHistoryDTO;
    }
}
