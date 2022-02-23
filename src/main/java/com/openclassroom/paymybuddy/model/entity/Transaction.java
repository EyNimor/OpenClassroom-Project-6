package com.openclassroom.paymybuddy.model.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.openclassroom.paymybuddy.annotation.ExcludeFromJacocoGeneratedReport;
import com.openclassroom.paymybuddy.model.entity.composedKey.TransactionComposedKey;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
@Entity @Table(name = "transactions") @ExcludeFromJacocoGeneratedReport
public class Transaction implements Serializable {

    @EmbeddedId
    private TransactionComposedKey key;
    @Column(name = "transaction_date")
    Date transactionDate;
    @Column(name = "amount")
    private float amount;
    @Column(name = "description")
    private String description;

    public Transaction(long numberId, User giver, User receiver, Date transactionDate, float amount) {
        this.setKey(new TransactionComposedKey(numberId, giver, receiver));
        this.setTransactionDate(transactionDate);
        this.setAmount(amount);
    }

    public Transaction(long numberId, User giver, User receiver, Date transactionDate, float amount, String description) {
        this.setKey(new TransactionComposedKey(numberId, giver, receiver));
        this.setTransactionDate(transactionDate);
        this.setAmount(amount);
        this.setDescription(description);
    }
    
}
