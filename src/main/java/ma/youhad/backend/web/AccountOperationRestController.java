package ma.youhad.backend.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youhad.backend.dtos.AccountHistoryDTO;
import ma.youhad.backend.dtos.AccountOperationDTO;
import ma.youhad.backend.exceptions.BankAccountNotFoundException;
import ma.youhad.backend.exceptions.InsufficientBalanceException;
import ma.youhad.backend.services.interfaces.AccountOperationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class AccountOperationRestController {

    private final AccountOperationService accountOperationService;

    @GetMapping("/account/{accountId}/operations")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<AccountOperationDTO> getHistory(@PathVariable String accountId){
        return accountOperationService.accountHistory(accountId);
    }

    @GetMapping("/operations")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<AccountOperationDTO> getAllOperations(){
        return accountOperationService.getAllOperations();
    }

    @GetMapping("/account/{accountId}/pageOperations")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public AccountHistoryDTO getAccountHistory(@PathVariable String accountId,
                                               @RequestParam(name="page", defaultValue = "0") int page,
                                               @RequestParam(name="size", defaultValue = "5") int size) throws BankAccountNotFoundException {
        return accountOperationService.getAccountHistory(accountId,page,size);
    }
    @PostMapping("/account/{accountId}/debit")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public void debit(@PathVariable String accountId, @RequestParam double amount, @RequestParam String description) throws BankAccountNotFoundException, InsufficientBalanceException {
        accountOperationService.debit(accountId,amount,description);

    }

    @PostMapping("/account/{accountId}/credit")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void credit(@PathVariable String accountId, @RequestParam double amount, @RequestParam String description) throws BankAccountNotFoundException{
        accountOperationService.credit(accountId,amount,description);

    }
    @PostMapping("/account/{accountId}/transfer")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void transfer(@PathVariable String accountId, @RequestParam String toAccountId, @RequestParam double amount) throws BankAccountNotFoundException, InsufficientBalanceException {
        accountOperationService.transfer(accountId,toAccountId,amount);

    }
}
