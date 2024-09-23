package dev.affoysal.banking.Service;


import dev.affoysal.banking.Entity.Account;
import dev.affoysal.banking.Exception.AccountNotFoundException;
import dev.affoysal.banking.Exception.DepositErrorException;
import dev.affoysal.banking.Exception.WithdrawErrorException;

import java.util.List;

public interface AccountService {

    Account createAccount(Account account);

    Account getAccountByID(Long id) throws AccountNotFoundException;

    Account deposit(Long id, double amount) throws AccountNotFoundException, DepositErrorException;

    Account withdraw(Long id, double amount) throws AccountNotFoundException, WithdrawErrorException;

    List<Account> getAllAccounts();

    void deleteAccountByID(Long id) throws AccountNotFoundException;
}
