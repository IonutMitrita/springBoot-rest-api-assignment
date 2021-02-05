package com.minimaldev.app.repository;

import com.minimaldev.app.model.Account;
import com.minimaldev.app.model.Transaction;
import com.minimaldev.app.model.TransactionDto;
import com.minimaldev.app.model.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT new com.minimaldev.app.model.TransactionDto(t.account.accountNumber, t.transactionTs, t.type, t.amount) " +
            "FROM Transaction t " +
            "WHERE t.account = :account")
    List<TransactionDto> findAllByAccount(Account account);

    @Query("SELECT new com.minimaldev.app.model.TransactionDto(t.account.accountNumber, t.transactionTs, t.type, t.amount) " +
            "FROM Transaction t " +
            "WHERE t.account = :account")
    Page<TransactionDto> findAllByAccount(Account account, Pageable pageable);



    @Query("SELECT new com.minimaldev.app.model.TransactionDto(t.account.accountNumber, t.transactionTs, t.type, t.amount) " +
            "FROM Transaction t " +
            "WHERE t.account = :account " +
            "AND t.transactionTs >= :start " +
            "AND t.transactionTs <= :end ")
    List<TransactionDto> findAll(Account account, Date start, Date end);

    @Query("SELECT new com.minimaldev.app.model.TransactionDto(t.account.accountNumber, t.transactionTs, t.type, t.amount) " +
            "FROM Transaction t " +
            "WHERE t.account = :account " +
            "AND t.transactionTs >= :start " +
            "AND t.transactionTs <= :end " +
            "AND t.type = :type")
    List<TransactionDto> findAll(Account account, TransactionType type, Date start, Date end);
}
