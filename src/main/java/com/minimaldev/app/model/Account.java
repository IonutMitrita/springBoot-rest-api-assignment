package com.minimaldev.app.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static javax.persistence.FetchType.LAZY;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
    //    @ToString.Exclude
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "account_number", updatable = false, nullable = false)
    private UUID accountNumber;

    @UpdateTimestamp
    private Date lastUpdateTimestamp;

    private double balance;

    @ToString.Exclude
    @OneToMany(fetch = LAZY)
    private List<Transaction> transactions;
}
