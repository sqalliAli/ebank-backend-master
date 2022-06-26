package org.bank.ebankbackend.dtos;

import lombok.Data;
import org.bank.ebankbackend.enums.AccountStatus;

import javax.persistence.Id;
import java.util.Date;


@Data
public class CurrentBankAccountDTO extends BankAccountDTO {

    private String id;
    private double balance;
    private Date createAt;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private double overDraft;
}
