package ma.youhad.backend.services.implementations;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youhad.backend.dtos.AccountOperationDTO;
import ma.youhad.backend.entities.AccountOperation;
import ma.youhad.backend.mappers.AccountOperationMapperImp;
import ma.youhad.backend.repositories.AccountOperationRepository;
import ma.youhad.backend.services.interfaces.AccountOperationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class AccountOperationServiceImpl implements AccountOperationService {

    private AccountOperationRepository accountOperationRepository;
    private AccountOperationMapperImp accountOperationMapperImp;

    @Override
    public List<AccountOperationDTO> accountHistory(String accountId){
        List<AccountOperation> accountOperations =accountOperationRepository.findByBankAccountId(accountId);
        return  accountOperations.stream().map(operation -> accountOperationMapperImp.
                fromAccountOperationToAccountOperationDTO(operation)).
                collect(Collectors.toList());
    }
}
