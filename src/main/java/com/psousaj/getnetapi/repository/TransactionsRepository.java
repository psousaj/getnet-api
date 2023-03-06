package com.psousaj.getnetapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psousaj.getnetapi.model.Transaction;

public interface TransactionsRepository extends JpaRepository<Transaction, String> {
    
}
