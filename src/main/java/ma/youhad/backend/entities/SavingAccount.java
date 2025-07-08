package ma.youhad.backend.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@DiscriminatorValue("SA")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SavingAccount  extends  BankAccount{
    private double interestRate;
}
