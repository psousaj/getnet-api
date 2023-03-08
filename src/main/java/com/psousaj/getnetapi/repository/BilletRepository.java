package com.psousaj.getnetapi.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psousaj.getnetapi.model.BilletTransaction;

public interface BilletRepository extends JpaRepository<BilletTransaction, UUID> {
    List<BilletTransaction> findByStatus(String status);
}
