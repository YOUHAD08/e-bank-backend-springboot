package ma.youhad.backend.mappers;

import lombok.AllArgsConstructor;
import ma.youhad.backend.dtos.SavingAccountDTO;
import ma.youhad.backend.entities.SavingAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class SavingAccountMapperImpl {

    private CustomerMapperImpl customerMapper;

    public SavingAccountDTO fromSavingAccountToSavingAccountDTO(SavingAccount savingAccount) {
        SavingAccountDTO savingAccountDTO = new SavingAccountDTO();
        BeanUtils.copyProperties(savingAccount, savingAccountDTO);
        savingAccountDTO.setCustomerDTO(customerMapper.fromCustomerToCustomerDTO(savingAccount.getCustomer()));
        savingAccountDTO.setAccountType(savingAccount.getClass().getSimpleName());
        return savingAccountDTO;

    }

    public SavingAccount fromSavingAccountDTOToSavingAccount(SavingAccountDTO savingAccountDTO) {
        SavingAccount savingAccount = new SavingAccount();
        BeanUtils.copyProperties(savingAccountDTO, savingAccount);
        savingAccount.setCustomer(customerMapper.fromCustomerDTOToCustomer(savingAccountDTO.getCustomerDTO()));
        return savingAccount;
    }
}
