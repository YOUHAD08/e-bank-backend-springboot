package ma.youhad.backend.dtos;
import lombok.Data;
import ma.youhad.backend.enums.AccountStatus;

import java.util.Date;

@Data
public class BankAccountDTO {
    private String id;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private String accountType ;
}
