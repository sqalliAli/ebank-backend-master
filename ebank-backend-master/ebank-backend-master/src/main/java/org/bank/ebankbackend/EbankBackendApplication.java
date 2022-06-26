package org.bank.ebankbackend;

import org.bank.ebankbackend.dtos.BankAccountDTO;
import org.bank.ebankbackend.dtos.CurrentBankAccountDTO;
import org.bank.ebankbackend.dtos.CustomerDTO;
import org.bank.ebankbackend.dtos.SavingBankAccountDTO;
import org.bank.ebankbackend.entities.*;
import org.bank.ebankbackend.enums.AccountStatus;
import org.bank.ebankbackend.enums.OperationType;
import org.bank.ebankbackend.exceptions.BalanceNotSufficientException;
import org.bank.ebankbackend.exceptions.BankAccountNotFoundException;
import org.bank.ebankbackend.exceptions.CustomerNotFoundException;
import org.bank.ebankbackend.repositories.AccountOperationReposutory;
import org.bank.ebankbackend.repositories.BankAccountReposutory;
import org.bank.ebankbackend.repositories.CustomerReposutory;
import org.bank.ebankbackend.services.BankAccountService;
import org.bank.ebankbackend.services.BankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService){
        return args -> {
            Stream.of("Hicham","Ghita","Zahra").forEach(name->{
                CustomerDTO customer = new CustomerDTO();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                bankAccountService.saveCustomer(customer);
            });
            bankAccountService.listCustomer().forEach(customer -> {
                try {
                    bankAccountService.saveCurrentBankAccount(Math.random()*90000,9000,customer.getId());
                    bankAccountService.saveSavingBankAccount(Math.random()*120000,5.5,customer.getId());

                } catch (CustomerNotFoundException e) {
                    e.printStackTrace();
                }
            });
            List<BankAccountDTO> bankAccounts = bankAccountService.bankAccountList();
            for (BankAccountDTO bankAccount:bankAccounts){
                for (int i=0; i<10;i++){
                    String accountId;
                    if(bankAccount instanceof SavingBankAccountDTO){
                        accountId = ((SavingBankAccountDTO) bankAccount).getId();
                    }else{
                        accountId = ((CurrentBankAccountDTO) bankAccount).getId();
                    }
                    bankAccountService.credit(accountId,10000+Math.random()*120000,"Credit");
                    bankAccountService.debit(accountId, 1000+Math.random()*9000,"Debit");
                }
            }
        };
    }

    //@Bean
    CommandLineRunner start(CustomerReposutory customerReposutory, BankAccountReposutory bankAccountReposutory, AccountOperationReposutory accountOperationReposutory) {
        return  args -> {
            Stream.of("hicham","assia","mohamed","lina").forEach(name->{
                Customer customer=new Customer();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                customerReposutory.save(customer);
            });
            customerReposutory.findAll().forEach(cust->{
                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random()*90000);
                currentAccount.setCreateAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCustomer(cust);
                currentAccount.setOverDraft(9000);
                bankAccountReposutory.save(currentAccount);

                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random()*90000);
                savingAccount.setCreateAt(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setCustomer(cust);
                savingAccount.setInterestRate(5.5);
                bankAccountReposutory.save(savingAccount);
            });
            bankAccountReposutory.findAll().forEach(acc->{
                for(int i=0; i<10; i++){
                    AccountOperation accountOperation = new AccountOperation();
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setAmount(Math.random()+12000);
                    accountOperation.setType(Math.random()>0.5? OperationType.DEBIT:OperationType.CREDIT);
                    accountOperation.setBankAccount(acc);
                    accountOperationReposutory.save(accountOperation);
                }

            });

        };

    }
}
