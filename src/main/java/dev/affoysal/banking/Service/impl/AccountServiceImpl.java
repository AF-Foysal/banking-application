package dev.affoysal.banking.Service.impl;

import dev.affoysal.banking.Entity.Account;
import dev.affoysal.banking.Exception.AccountNotFoundException;
import dev.affoysal.banking.Exception.DepositErrorException;
import dev.affoysal.banking.Exception.WithdrawErrorException;
import dev.affoysal.banking.Repository.AccountRepository;
import dev.affoysal.banking.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account getAccountByID(Long id) throws AccountNotFoundException {
        return accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
    }

    @Override
    public Account deposit(Long id, double amount) throws AccountNotFoundException, DepositErrorException {
        if (amount < 0) {
            throw new DepositErrorException();
        }
        Account account = accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
        account.setBalance(account.getBalance() + amount);
        return accountRepository.save(account);
    }

    @Override
    public Account withdraw(Long id, double amount) throws AccountNotFoundException, WithdrawErrorException {
        Account account = accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
        if ((account.getBalance() < amount) || amount < 0) {
            throw new WithdrawErrorException();
        }
        account.setBalance(account.getBalance() - amount);
        return accountRepository.save(account);

    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public void deleteAccountByID(Long id) throws AccountNotFoundException {
        if (accountRepository.existsById(id)) {
            accountRepository.deleteById(id);
        } else {
            throw new AccountNotFoundException();
        }
    }
}
