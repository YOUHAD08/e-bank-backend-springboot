package ma.youhad.backend.mappers;

import ma.youhad.backend.dtos.AccountOperationDTO;
import ma.youhad.backend.entities.AccountOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class AccountOperationMapperImp {
    public AccountOperationDTO fromAccountOperationToAccountOperationDTO(AccountOperation accountOperation) {
        AccountOperationDTO accountOperationDTO = new AccountOperationDTO();
        BeanUtils.copyProperties(accountOperation, accountOperationDTO);
       return accountOperationDTO ;
    }

    public AccountOperation fromAccountOperationDTOToAccountOperation(AccountOperationDTO accountOperationDTO) {
        AccountOperation accountOperation = new AccountOperation();
        BeanUtils.copyProperties(accountOperationDTO, accountOperation);
        return accountOperation;
    }
}

