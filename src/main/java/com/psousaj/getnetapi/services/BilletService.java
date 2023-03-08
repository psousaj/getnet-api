package com.psousaj.getnetapi.services;

import java.net.URI;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.psousaj.getnetapi.model.BilletTransaction;
import com.psousaj.getnetapi.repository.BilletRepository;
import com.psousaj.getnetapi.utils.ErrorResponse;
import com.psousaj.getnetapi.utils.Validator;

@Service
public class BilletService {
    @Autowired
    public BilletRepository repository;

    public ResponseEntity<BilletTransaction> saveTransaction(BilletTransaction transaction) {
        BilletTransaction savedTransaction = repository.save(transaction);
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("taxcontabilidade.herokuapp.com/")
                // .port(8080)
                .path("/api/v1/conciliation/billet/transactions/{id}")
                .buildAndExpand(savedTransaction.getId());
        URI uri = uriComponents.toUri();

        // return
        // ResponseEntity.status(HttpStatus.CREATED).body(repository.save(transaction));
        return ResponseEntity.created(uri).body(savedTransaction);
    }

    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    public ResponseEntity<?> findByStatus(String status) {
        status = status.toUpperCase();
        if (!Validator.validateStatus(status)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                    String.format("status: %s", status),
                    "Tipo de transação inválido. Only: (PENDING, DENIED, ERROR, PAID)"));
        }
        return ResponseEntity.ok(repository.findByStatus(status));
    }

    public boolean idExists(UUID id) {
        return repository.findById(id).isPresent();
    }

    public ResponseEntity<?> findById(UUID id) {
        if (!idExists(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), String.format("Id: %s", id),
                            "Transação não encontrada"));
        }

        return ResponseEntity.ok(repository.findById(id));
    }
}
