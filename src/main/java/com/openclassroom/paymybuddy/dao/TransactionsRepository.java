package com.openclassroom.paymybuddy.dao;

import com.openclassroom.paymybuddy.model.entity.Transaction;
import com.openclassroom.paymybuddy.model.entity.composedKey.TransactionComposedKey;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionsRepository extends JpaRepository<Transaction, TransactionComposedKey> {
    
}
