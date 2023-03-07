package com.psousaj.getnetapi.services;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.psousaj.getnetapi.model.Transaction;
import com.psousaj.getnetapi.repository.TransactionsRepository;
import com.psousaj.getnetapi.utils.ErrorResponse;
import com.psousaj.getnetapi.utils.Validator;

@Service
public class TransactionsService {
    
    @Autowired
    private TransactionsRepository repository;

    public boolean validateCustomer(String customerId) {
        return repository.findByCustomerId(customerId).isPresent();
    }

    public Optional<Transaction> findById(UUID id) {
        return repository.findById(id);
    }

    public ResponseEntity<?> findAllByFilter(String status) {
        return validateStatus(status);
    }

    public ResponseEntity<List<Transaction>> findAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    public ResponseEntity<?> findByFilter(String type, String status, String customerId) {
        return validateFields(customerId, type, status);
    }

    public ResponseEntity<?> findByCustomerId(String type, String customerId) {
        return validateFields(customerId, type);
    }

    public ResponseEntity<?> findByPaymentType(String type) {
        return ResponseEntity.ok(repository.findByPaymentType(type));
    }

    public ResponseEntity<?> findByPaymentTypeAndStatus(String type, String status) {
        return validateStatus(status, type);
    }

    public ResponseEntity<Object> findTransaction(UUID id) {
        String error = ("Transaçao não encontrada. Verifique UUID.");
        var transaction = repository.findById(id);

        if (!transaction.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), String.format("Id: %s", id), error));
        }
        return ResponseEntity.ok(transaction.get());
    }

    public ResponseEntity<Transaction> saveTransaction(Transaction transaction){
        Transaction savedTransaction = repository.save(transaction);
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(8080)
                .path("/api/{type}/transactions/{id}")
                .buildAndExpand(savedTransaction.getPaymentType(), savedTransaction.getPaymentId());
        URI uri = uriComponents.toUri();

        // return
        // ResponseEntity.status(HttpStatus.CREATED).body(repository.save(transaction));
        return ResponseEntity.created(uri).body(savedTransaction);
    }

    public ResponseEntity<?> validateFields(String customerId, String type, String status) {
        status = status.toUpperCase();
        if (!Validator.validateStatus(status) && !validateCustomer(customerId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                            String.format("Id: %s - status:%s", customerId, status),
                            "Id de Lojista e tipo de transação inválidos"));
        } else if (!Validator.validateStatus(status)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                    String.format("status: %s", status),
                    "Tipo de transação inválido. Only: (APPROVED, AUTHORIZED, PENDING, CONFIRMED, CANCELLED, DENIED, ERROR)"));
        } else if (!validateCustomer(customerId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                            String.format("Id: %s", customerId),
                            "Id de Lojista não encontrado. Verifique e tente novamente"));
        }

        return ResponseEntity.ok(repository.findByFilter(type, status, customerId));
    }

    public ResponseEntity<?> validateFields(String customerId, String type) {
        if (!validateCustomer(customerId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                            String.format("Id: %s", customerId),
                            "Id de Lojista não encontrado. Verifique e tente novamente"));
        }

        return ResponseEntity.ok(repository.findByCustomerId(type, customerId));
    }

    public ResponseEntity<?> validateStatus(String status, String type) {
        status = status.toUpperCase();
        if (!Validator.validateStatus(status)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                    String.format("status: %s", status),
                    "Tipo de transação inválido. Only: (APPROVED, AUTHORIZED, PENDING, CONFIRMED, CANCELLED, DENIED, ERROR)"));
        }
        return ResponseEntity.ok(repository.findByPaymentTypeAndStatus(type, status));
    }

    public ResponseEntity<?> validateStatus(String status) {
        status = status.toUpperCase();
        if (!Validator.validateStatus(status)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    String.format("status: %s", status),
                    "Tipo de transação inválido. Only: (APPROVED, AUTHORIZED, PENDING, CONFIRMED, CANCELLED, DENIED, ERROR)"));
        }
        return ResponseEntity.ok(repository.findAllByFilter(status));
    }
}
