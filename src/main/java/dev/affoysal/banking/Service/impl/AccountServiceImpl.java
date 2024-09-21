package dev.affoysal.banking.Service.impl;

import dev.affoysal.banking.Entity.Account;
import dev.affoysal.banking.Repository.AccountRepository;
import dev.affoysal.banking.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }
}
