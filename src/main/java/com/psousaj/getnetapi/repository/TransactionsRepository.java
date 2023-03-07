package com.psousaj.getnetapi.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.psousaj.getnetapi.model.Transaction;

public interface TransactionsRepository extends JpaRepository<Transaction, UUID> {

    public List<Transaction> findByPaymentType(String type);

    public Optional<?> findByCustomerId(String customerId);

    @Query("SELECT t FROM Transaction t WHERE t.paymentType = :type AND t.status = :status")
    public List<Transaction> findByPaymentTypeAndStatus(String type, String status);

    @Query("SELECT t FROM Transaction t WHERE t.paymentType = :type AND t.customerId = :customerId")
    public List<Transaction> findByCustomerId(String type, String customerId);

    @Query("SELECT t FROM Transaction t WHERE t.paymentType = :type AND t.status = :status AND t.customerId = :customerId")
    public List<Transaction> findByFilter(String type, String status, String customerId);
    
    @Query("SELECT t FROM Transaction t WHERE t.status = :status")
    public List<Transaction> findAllByFilter(String status);
}
