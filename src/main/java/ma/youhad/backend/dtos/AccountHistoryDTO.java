package ma.youhad.backend.dtos;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import ma.youhad.backend.enums.AccountStatus;
import ma.youhad.backend.enums.AccountType;
import java.util.Date;
import java.util.List;

@Data
public class AccountHistoryDTO {
    private String customerName;
    private String accountId;
    @Enumerated(EnumType.STRING)
    private AccountType accountType ;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    private Date createdAt;
    private double balance;
    private int currentPage;
    private int pageSize;
    private int totalPages;
    private List<AccountOperationDTO> accountOperationDTOs;
}
