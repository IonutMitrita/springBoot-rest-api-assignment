package com.minimaldev.app.controller;

import com.minimaldev.app.model.Account;
import com.minimaldev.app.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
@AllArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("create")
    public ResponseEntity<Account> createAccount() {
        Account account = accountService.create();
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAccounts() {
        List<Account> accounts = accountService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(accounts);
    }

    @GetMapping("{accountNumber}/balance")
    public ResponseEntity getBalance(@PathVariable UUID accountNumber) {
        double balance = accountService.getAccount(accountNumber).getBalance();
        return ResponseEntity.status(HttpStatus.OK).body(balance);
    }

}
