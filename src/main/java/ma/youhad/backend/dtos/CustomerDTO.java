package ma.youhad.backend.dtos;

import jakarta.persistence.*;
import lombok.Data;
import ma.youhad.backend.entities.BankAccount;

import java.util.List;


@Data
public class CustomerDTO {
    private Long id;
    private String name;
    private String email;

}