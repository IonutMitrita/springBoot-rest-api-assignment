package com.minimaldev.app.service;

import com.minimaldev.app.exceptions.AccountDoesNotExistException;
import com.minimaldev.app.model.Account;
import com.minimaldev.app.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class AccountService {
    private final AccountRepository accountRepository;

    public Account create() {
        Account createdAcc = accountRepository.save(Account.builder().balance(0).build());
        return createdAcc;
    }

    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    public Account getAccount(UUID accountNumber) {
        return accountRepository.findById(accountNumber)
                .orElseThrow(() -> new AccountDoesNotExistException(accountNumber.toString()));
    }
}
