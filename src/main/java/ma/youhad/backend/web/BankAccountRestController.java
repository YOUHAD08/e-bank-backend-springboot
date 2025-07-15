package ma.youhad.backend.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youhad.backend.dtos.BankAccountDTO;
import ma.youhad.backend.dtos.CurrentAccountDTO;
import ma.youhad.backend.dtos.SavingAccountDTO;
import ma.youhad.backend.exceptions.BankAccountNotFoundException;
import ma.youhad.backend.exceptions.CustomerNotFoundException;
import ma.youhad.backend.services.interfaces.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class BankAccountRestController {

    private BankAccountService bankAccountService;

   @GetMapping("/account/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
       log.info("Get BankAccount by id");
        return bankAccountService.getBankAccount(accountId);
    }

    @GetMapping("/accounts")
    public List<BankAccountDTO> getBankAccounts() {
       log.info("Get All BankAccounts");
       return bankAccountService.getAllBankAccounts();
    }


    @PostMapping("/currentAccount/{customerId}" )
    public CurrentAccountDTO createCurrentAccount(@RequestBody CurrentAccountDTO currentAccountDTO, @PathVariable long customerId) throws CustomerNotFoundException {
       log.info("Create Current Account");
       return bankAccountService.createCurrentBankAccount(currentAccountDTO,customerId);
    }

    @PostMapping("/savingAccount/{customerId}" )
    public SavingAccountDTO createSavingAccount(@RequestBody SavingAccountDTO savingAccountDTO , @PathVariable long customerId) throws CustomerNotFoundException {
        log.info("Create Saving Account");
        return bankAccountService.createSavingBankAccount(savingAccountDTO,customerId);
    }

    @PutMapping("/currentAccount/{accountId}")
    public CurrentAccountDTO updateCurrentAccount(@PathVariable String accountId, @RequestBody CurrentAccountDTO currentAccountDTO)  {
       log.info("Update Current Bank Account by id");
        currentAccountDTO.setId(accountId);
       return bankAccountService.updateCurrentAccount(accountId, currentAccountDTO);
    }

    @PutMapping("/savingAccount/{accountId}")
    public SavingAccountDTO updateSavingAccount(@PathVariable String accountId, @RequestBody SavingAccountDTO savingAccountDTO)  {
        log.info("Update Saving Bank Account by id");
        savingAccountDTO.setId(accountId);
        return bankAccountService.updateSavingAccount(accountId, savingAccountDTO);
    }

    @DeleteMapping("/account/{accountId}")
    public void deleteBankAccount(@PathVariable String accountId) {
       log.info("Delete Bank Account by id");
       bankAccountService.deleteBankAccount(accountId);
    }


}
