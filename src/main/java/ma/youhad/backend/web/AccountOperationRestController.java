package ma.youhad.backend.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youhad.backend.dtos.AccountOperationDTO;
import ma.youhad.backend.services.interfaces.AccountOperationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class AccountOperationRestController {

    private final AccountOperationService accountOperationService;

    @GetMapping("/account/{accountId}/operations")
    public List<AccountOperationDTO> getHistory(@PathVariable String accountId){
        return accountOperationService.accountHistory(accountId);
    }
}
