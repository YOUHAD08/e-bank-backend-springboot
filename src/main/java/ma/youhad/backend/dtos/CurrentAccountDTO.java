package ma.youhad.backend.dtos;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import ma.youhad.backend.enums.AccountStatus;

import java.util.Date;

@Data
public class CurrentAccountDTO {
    private String id;
    private double balance;
    private Date createdAt;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private double overdraft;
}
