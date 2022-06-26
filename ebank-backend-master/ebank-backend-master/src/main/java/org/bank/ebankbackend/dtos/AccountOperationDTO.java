package org.bank.ebankbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bank.ebankbackend.entities.BankAccount;
import org.bank.ebankbackend.enums.OperationType;

import javax.persistence.*;
import java.util.Date;


@Data
public class AccountOperationDTO {
    private Long id;
    private Date operationDate;
    private double amount;
    private OperationType type;
    private String description;

}
