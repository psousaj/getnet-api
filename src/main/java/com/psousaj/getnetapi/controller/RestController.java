package com.psousaj.getnetapi.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.psousaj.getnetapi.model.Transaction;
import com.psousaj.getnetapi.services.TransactionsService;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api/v1")
public class RestController {
    // "/api/conciliation"
    @Autowired
    private TransactionsService service;

    @GetMapping()
    public ModelAndView welcome(){
        return new ModelAndView("index");
    }

    @GetMapping("/{type}/transactions/{id}")
    public ResponseEntity<?> findTransaction(@PathVariable String type, @PathVariable UUID id) {
        return service.findTransaction(id);
    }

    @GetMapping("/{type}/transactions/filter")
    public ResponseEntity<?> findByFilter(@PathVariable String type,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String customerId) {

        if (customerId != null && status != null) {
            return service.findByFilter(type, status, customerId);
        }
        if (status != null) {
            return service.findByPaymentTypeAndStatus(type, status);
        }

        return service.findByCustomerId(type, customerId);
    }

    @GetMapping("/{type}/transactions")
    public ResponseEntity<?> findAllByType(@PathVariable String type) {
        return service.findByPaymentType(type);
    }

    @GetMapping("/transactions")
    public ResponseEntity<?> allTransactions(@RequestParam(required = false) String status) {
        if (status != null) {
            return service.findAllByFilter(status);
        }
        return service.findAll();
    }

    @GetMapping("/{type}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Transaction> saveTransaction(@PathVariable String type,
            @RequestParam(required = true) String payment_type,
            @RequestParam(required = true) String customer_id,
            @RequestParam(required = true) String order_id,
            @RequestParam(required = true) UUID payment_id,
            @RequestParam(required = true) String amount,
            @RequestParam(required = true) String status,
            @RequestParam(required = false) Integer number_installments,
            @RequestParam(required = false) String acquirer_transaction_id,
            @RequestParam(required = false) String authorization_timestamp,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String terminal_nsu,
            @RequestParam(required = false) String authorization_code,
            @RequestParam(required = false) String description_detail,
            @RequestParam(required = false) String error_code) {

        String dataHora = null;
        try {
            LocalDateTime firstDate = LocalDateTime
                    .parse(authorization_timestamp,
                            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                                    .withZone(ZoneId.of("UTC")));
            dataHora = firstDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        } catch (Exception e) {
        }

        Transaction actualTransaction = Transaction.builder()
                .paymentType(payment_type)
                .paymentId(payment_id)
                .customerId(customer_id)
                .orderId(order_id)
                .amount(amount)
                .status(status)
                .installments(number_installments)
                .acquirerTransactionId(acquirer_transaction_id)
                .authorizationTime(dataHora)
                .brand(brand)
                .nsu(terminal_nsu)
                .authorizationCode(authorization_code)
                .descriptionDetail(description_detail)
                .errorCode(error_code)
                .build();
                                                        
        return service.saveTransaction(actualTransaction);
    }
}

