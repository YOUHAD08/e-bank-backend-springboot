package ma.youhad.backend.entities;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@DiscriminatorValue("CA")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrentAccount extends  BankAccount {
    private double overdraft;
}
