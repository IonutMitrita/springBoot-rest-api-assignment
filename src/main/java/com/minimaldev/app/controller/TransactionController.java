package com.minimaldev.app.controller;

import com.minimaldev.app.model.TransactionDto;
import com.minimaldev.app.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
@AllArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionDto> performTransaction(@ModelAttribute TransactionDto transactionDto) {
        transactionDto = transactionService.performTransaction(transactionDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(transactionDto);
    }

    @GetMapping("/byaccountnumber/{accountNumber}")
    public ResponseEntity<List<TransactionDto>> getTransactions(@PathVariable UUID accountNumber) {
        List<TransactionDto> transactions = transactionService.getTransactions(accountNumber);
        return ResponseEntity.status(HttpStatus.OK)
                .body(transactions);
    }

    @GetMapping("/byaccountnumber/{accountNumber}/{pageNumber}")
    public ResponseEntity<Page<TransactionDto>> getTransactionsByPage(@PathVariable UUID accountNumber, @PathVariable int pageNumber) {
        Page<TransactionDto> transactions = transactionService.getTransactionsByPage(accountNumber, pageNumber);
        return ResponseEntity.status(HttpStatus.OK)
                .body(transactions);
    }

    @GetMapping("byaccountnumber/{accountNumber}/time")
    public ResponseEntity<List<TransactionDto>> getTransactionsByTime(
            @PathVariable("accountNumber") UUID accountNumber,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) {
        List<TransactionDto> transactionsByTime = transactionService.getTransactionsByTime(accountNumber, startDate, endDate);
        return ResponseEntity.status(HttpStatus.OK)
                .body(transactionsByTime);

    }

    @GetMapping("byaccountnumber/{accountNumber}/withdrawals/time")
    public ResponseEntity<List<TransactionDto>> getWithdrawTransactionsByTime(
            @PathVariable("accountNumber") UUID accountNumber,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) {
        List<TransactionDto> transactionsByTime = transactionService.getWithdrawalsByTime(accountNumber, startDate, endDate);
        return ResponseEntity.status(HttpStatus.OK)
                .body(transactionsByTime);

    }

}
