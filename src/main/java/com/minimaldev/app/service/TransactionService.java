package com.minimaldev.app.service;

import com.minimaldev.app.exceptions.AccountDoesNotExistException;
import com.minimaldev.app.model.Account;
import com.minimaldev.app.model.Transaction;
import com.minimaldev.app.model.TransactionDto;
import com.minimaldev.app.model.TransactionType;
import com.minimaldev.app.repository.AccountRepository;
import com.minimaldev.app.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class TransactionService {
    public static final int DEFAULT_PAGE_SIZE = 10;
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionDto performTransaction(TransactionDto transactionDto) {
        UUID accountNumber = transactionDto.getAccountNumber();
        Account account = accountRepository.findById(accountNumber)
                .orElseThrow(() -> new AccountDoesNotExistException(accountNumber.toString()));

        double amount = transactionDto.getAmount();
        TransactionType transactionType = getTransactionType(amount);


        Transaction transaction = Transaction.builder()
                .account(account)
                .amount(amount)
                .type(transactionType)
                .build();
        transaction = transactionRepository.save(transaction);
        account.setBalance(updateBalance(account, amount));

        transactionDto.setTransactionTs(transaction.getTransactionTs());
        transactionDto.setType(transaction.getType());

        return transactionDto;
    }

    private double updateBalance(Account account, double amount) {
        return account.getBalance() + amount;
    }

    public List<TransactionDto> getTransactions(UUID accountNumber) {
        Account account = accountRepository.findById(accountNumber)
                .orElseThrow(() -> new AccountDoesNotExistException(accountNumber.toString()));

        List<TransactionDto> allByAccount = transactionRepository.findAllByAccount(account);
        return allByAccount;
    }

    public Page<TransactionDto> getTransactionsByPage(UUID accountNumber, int pageNumber) {
        Account account = accountRepository.findById(accountNumber)
                .orElseThrow(() -> new AccountDoesNotExistException(accountNumber.toString()));

        Pageable pageable = PageRequest.of(0, DEFAULT_PAGE_SIZE);

        Page<TransactionDto> allByAccount = transactionRepository.findAllByAccount(account, pageable);
        return allByAccount;
    }

    private TransactionType getTransactionType(double amount) {
        return amount > 0 ? TransactionType.DEPOSIT : TransactionType.WITHDRAW;
    }

    public List<TransactionDto> getTransactionsByTime(UUID accountNumber, String startDateRange, String endDateRange) {
        Account account = accountRepository.findById(accountNumber)
                .orElseThrow(() -> new AccountDoesNotExistException(accountNumber.toString()));

        Date startDate = parseDate(startDateRange);
        Date endDate = parseDate(endDateRange);
        List<TransactionDto> transactions = transactionRepository.findAll(account, startDate, endDate);
        return transactions;

    }

    public List<TransactionDto> getWithdrawalsByTime(UUID accountNumber, String startDateRange, String endDateRange) {
        Account account = accountRepository.findById(accountNumber)
                .orElseThrow(() -> new AccountDoesNotExistException(accountNumber.toString()));

        Date startDate = parseDate(startDateRange);
        Date endDate = parseDate(endDateRange);
        List<TransactionDto> transactions = transactionRepository.findAll(account,TransactionType.WITHDRAW, startDate, endDate);
        return transactions;

    }

    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
}
