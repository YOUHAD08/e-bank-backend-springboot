package ma.youhad.backend.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youhad.backend.dtos.AccountHistoryDTO;
import ma.youhad.backend.dtos.AccountOperationDTO;
import ma.youhad.backend.exceptions.BankAccountNotFoundException;
import ma.youhad.backend.exceptions.InsufficientBalanceException;
import ma.youhad.backend.services.interfaces.AccountOperationService;
import ma.youhad.backend.services.interfaces.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class AccountOperationRestController {

    private final AccountOperationService accountOperationService;
    private BankAccountService bankAccountService;

    @GetMapping("/account/{accountId}/operations")
    public List<AccountOperationDTO> getHistory(@PathVariable String accountId){
        return accountOperationService.accountHistory(accountId);
    }

    @GetMapping("/account/{accountId}/pageOperations")
    public AccountHistoryDTO getAccountHistory(@PathVariable String accountId,
                                               @RequestParam(name="page", defaultValue = "0") int page,
                                               @RequestParam(name="size", defaultValue = "5") int size) throws BankAccountNotFoundException {
        return accountOperationService.getAccountHistory(accountId,page,size);
    }
    @PostMapping("/account/{accountId}/debit")
    public void debit(@PathVariable String accountId, @RequestParam double amount, @RequestParam String description) throws BankAccountNotFoundException, InsufficientBalanceException {
        bankAccountService.debit(accountId,amount,description);

    }

    @PostMapping("/account/{accountId}/credit")
    public void credit(@PathVariable String accountId, @RequestParam double amount, @RequestParam String description) throws BankAccountNotFoundException{
        bankAccountService.credit(accountId,amount,description);

    }
    @PostMapping("/account/{fromCustomerId}/transfer")
    public void transfer(@PathVariable String fromCustomerId, @RequestParam String toCustomerId, @RequestParam double amount) throws BankAccountNotFoundException, InsufficientBalanceException {
        bankAccountService.transfer(fromCustomerId,toCustomerId,amount);

    }
}
