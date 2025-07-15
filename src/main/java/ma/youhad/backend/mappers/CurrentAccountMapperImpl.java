package ma.youhad.backend.mappers;

import lombok.AllArgsConstructor;
import ma.youhad.backend.dtos.CurrentAccountDTO;
import ma.youhad.backend.dtos.CustomerDTO;
import ma.youhad.backend.entities.CurrentAccount;
import ma.youhad.backend.entities.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class CurrentAccountMapperImpl {

    private CustomerMapperImpl customerMapper;

    public CurrentAccountDTO fromCurrentAccountToCurrentAccountDTO(CurrentAccount currentAccount) {
        CurrentAccountDTO currentAccountDTO = new CurrentAccountDTO();
        BeanUtils.copyProperties(currentAccount, currentAccountDTO);
        currentAccountDTO.setCustomerDTO(customerMapper.fromCustomerToCustomerDTO(currentAccount.getCustomer()));
        currentAccountDTO.setAccountType(currentAccount.getClass().getSimpleName());
        return currentAccountDTO;
    }

    public CurrentAccount fromCurrentAccountDTOToCurrentAccount(CurrentAccountDTO currentAccountDTO) {
        CurrentAccount currentAccount = new CurrentAccount();
        BeanUtils.copyProperties(currentAccountDTO, currentAccount);
        currentAccount.setCustomer(customerMapper.fromCustomerDTOToCustomer(currentAccountDTO.getCustomerDTO()));
        return currentAccount;
    }
}
