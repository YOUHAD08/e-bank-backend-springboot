package ma.youhad.backend.dtos;

import lombok.Data;
import ma.youhad.backend.enums.OperationType;
import java.util.Date;


@Data
public class AccountOperationDTO {
    private Long id;
    private Date operationDate;
    private double amount;
    private OperationType type;
    private String description;
    private String accountId;
    private String CustomerName;
}
