package ma.youhad.backend.dtos;

import lombok.Data;

@Data
public class CurrentAccountDTO extends BankAccountDTO {
    private double overdraft;
}
