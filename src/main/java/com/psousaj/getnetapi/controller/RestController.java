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
import org.springframework.web.servlet.view.RedirectView;

import com.psousaj.getnetapi.model.BilletTransaction;
import com.psousaj.getnetapi.model.Transaction;
import com.psousaj.getnetapi.services.BilletService;
import com.psousaj.getnetapi.services.TransactionsService;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api/v1/conciliation")
public class RestController {
    // "/api/conciliation"
    @Autowired
    private TransactionsService service;
    @Autowired
    private BilletService billetService;

    @GetMapping()
    public ModelAndView welcome() {
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

    @GetMapping("/billet")
    public ResponseEntity<?> saveBilletTransaction(@RequestParam(required = false) String payment_type,
            @RequestParam(required = false) String order_id,
            @RequestParam(required = false) String payment_id,
            @RequestParam(required = true) UUID id,
            @RequestParam(required = true) String amount,
            @RequestParam(required = true) String status,
            @RequestParam(required = false) String bank,
            @RequestParam(required = false) String typeful_line,
            @RequestParam(required = false) String issue_date,
            @RequestParam(required = false) String expiration_date,
            @RequestParam(required = false) String payment_date,
            @RequestParam(required = false) String error_code,
            @RequestParam(required = false) String description_detail) {

        issue_date = parseDate(issue_date);
        expiration_date = parseDate(expiration_date);
        payment_date = parseDate(payment_date);

        BilletTransaction actualTransaction = BilletTransaction.builder()
                // .paymentType(payment_type)
                .orderId(order_id)
                .paymentId(payment_id)
                .id(id)
                .amount(amount)
                .status(status)
                .bank(bank)
                .status(status)
                .status(status)
                .status(status)
                .status(status)
                .descriptionDetail(description_detail)
                .errorCode(error_code)
                .build();

        return billetService.saveTransaction(actualTransaction);
    }

    @GetMapping("/billet/transactions")
    public ResponseEntity<?> findBilletTransactions(@RequestParam(required = false) String status) {
        if (status != null) {
            return billetService.findByStatus(status);
        }
        return billetService.findAll();
    }

    @GetMapping("/credit")
    public RedirectView redirectToCreditTransactions() {
        return new RedirectView("./credit/transactions");
    }

    @GetMapping("/debit")
    public RedirectView redirectToDebitTransactions() {
        return new RedirectView("./debit/transactions");
    }

    public String parseDate(String date) {
        LocalDateTime firstDate;
        try {
            firstDate = LocalDateTime
                    .parse(date,
                            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                                    .withZone(ZoneId.of("UTC")));

            return firstDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        } catch (Exception e) {
            return null;
        }
    }
}
