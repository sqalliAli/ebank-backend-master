package org.bank.ebankbackend.dtos;

import lombok.Data;

import org.bank.ebankbackend.enums.AccountStatus;

import javax.persistence.*;
import java.util.Date;


@Data
public class SavingBankAccountDTO extends BankAccountDTO {

    private String id;
    private double balance;
    private Date createAt;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private double interestRate;
}
