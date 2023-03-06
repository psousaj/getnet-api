package com.psousaj.getnetapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.psousaj.getnetapi.model.Transaction;
import com.psousaj.getnetapi.repository.TransactionsRepository;

@Service
public class TransactionsService {
    
    @Autowired
    private TransactionsRepository repository;

    public ResponseEntity<Transaction> saveTransaction(Transaction transaction){
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(transaction));
    }
}
