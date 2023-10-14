package com.example.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.example.entity.Account;
import java.util.Optional;

import com.example.repository.AccountRepository;

@Service
public class AccountService {

    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccountByUsername(String username) {
        Optional<Account> optionalAccount = accountRepository.findAccountByUsername(username);
        if(optionalAccount.isPresent()){
            return optionalAccount.get();
        }else{
            return null;
        }
        
    }

    public Account getAccountById(int id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if(optionalAccount.isPresent()){
            return optionalAccount.get();
        }else{
            return null;
        }
        
    }

    public Account persistAccount(Account account) {
        return accountRepository.save(account);
    }
}
