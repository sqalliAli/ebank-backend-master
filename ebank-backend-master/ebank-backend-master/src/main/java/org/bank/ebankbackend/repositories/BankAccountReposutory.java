package org.bank.ebankbackend.repositories;

import org.bank.ebankbackend.entities.BankAccount;
import org.bank.ebankbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountReposutory extends JpaRepository<BankAccount,String> {
}
