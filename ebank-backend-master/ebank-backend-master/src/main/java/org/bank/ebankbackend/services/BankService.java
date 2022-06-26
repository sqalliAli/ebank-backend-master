package org.bank.ebankbackend.services;

import org.bank.ebankbackend.entities.BankAccount;
import org.bank.ebankbackend.entities.CurrentAccount;
import org.bank.ebankbackend.entities.SavingAccount;
import org.bank.ebankbackend.repositories.BankAccountReposutory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BankService {
    @Autowired
    private BankAccountReposutory bankAccountReposutory;
    public void consulter(){
        BankAccount bankAccount =
                bankAccountReposutory.findById("2a31f2d6-67dd-4dfc-9387-468d276baa2d").orElse(null);
        if(bankAccount!=null) {
            System.out.printf("*****************************\n");
            System.out.println(bankAccount.getId());
            System.out.println(bankAccount.getBalance());
            System.out.println(bankAccount.getStatus());
            System.out.println(bankAccount.getCreateAt());
            System.out.println(bankAccount.getCustomer().getName());
            System.out.println(bankAccount.getClass().getSimpleName());
            if (bankAccount instanceof CurrentAccount) {
                System.out.println("Over Draft=>" + ((CurrentAccount) bankAccount).getOverDraft());
            } else if (bankAccount instanceof SavingAccount) {
                System.out.println("Rate=>" + ((SavingAccount) bankAccount).getInterestRate());
            }
            bankAccount.getAccountOperations().forEach(op -> {
                System.out.println(op.getType() + "\t" + op.getOperationDate() + "\t" + op.getAmount());
            });
        }
    }
}
