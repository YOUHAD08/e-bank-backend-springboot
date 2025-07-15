package ma.youhad.backend.web;

import lombok.AllArgsConstructor;
import ma.youhad.backend.services.interfaces.BankAccountService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class BankAccountRestController {

    BankAccountService bankAccountService;


}
