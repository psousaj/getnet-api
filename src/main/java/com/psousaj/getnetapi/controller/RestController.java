package com.psousaj.getnetapi.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
            @RequestParam(required = false) String transaction_id,
            @RequestParam(required = false) String transaction_timestamp,
            @RequestParam(required = false) Integer number_installments,
            @RequestParam(required = false) String acquirer_transaction_id,
            @RequestParam(required = false) String authorization_timestamp,
            @RequestParam(required = false) String receiver_psp_name,
            @RequestParam(required = false) String receiver_psp_code,
            @RequestParam(required = false) String receiver_name,
            @RequestParam(required = false) String receiver_cnpj,
            @RequestParam(required = false) String receiver_cpf,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String terminal_nsu,
            @RequestParam(required = false) String authorization_code,
            @RequestParam(required = false) String description_detail,
            @RequestParam(required = false) String error_code
            ) {

        String dataHora = parseDate(authorization_timestamp);
        String transactionTime = parseDate(transaction_timestamp);
  

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
                .transactionId(transaction_id)
                .transactionTime(transactionTime)
                .receiverPspName(receiver_psp_name)
                .receiverPspCode(receiver_psp_code)
                .receiverName(receiver_name)
                .receiverCnpj(receiver_cnpj)
                .receiverCpf(receiver_cpf)
                .errorCode(error_code)
                .build();
                                                        
        return service.saveTransaction(actualTransaction);
    }

    @GetMapping("/billet")
    public ResponseEntity<?> saveBilletTransaction(@RequestParam(required = false) String payment_type,
            @RequestParam(required = false) String order_id,
            @RequestParam(required = false) String payment_id,
            @RequestParam(required = false) UUID id,
            @RequestParam(required = true) String amount,
            @RequestParam(required = true) String status,
            @RequestParam(required = false) String bank,
            @RequestParam(required = false) String typeful_line,
            @RequestParam(required = false) String issue_date,
            @RequestParam(required = false) String expiration_date,
            @RequestParam(required = false) String payment_date,
            @RequestParam(required = false) String error_code,
            @RequestParam(required = false) String description_detail) {

        String issueDate = parseDate(issue_date);
        String expirationDate = parseDate(expiration_date);
        String paymentDate = parseDate(payment_date);

        BilletTransaction actualTransaction = BilletTransaction.builder()
                // .paymentType(payment_type)
                .orderId(order_id)
                .paymentId(payment_id)
                .id(id)
                .amount(amount)
                .status(status)
                .bank(bank)
                .typefulLine(typeful_line)
                .issueDate(issueDate)
                .expirationDate(expirationDate)
                .paymentDate(paymentDate)
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

//    public String parseDate(String date) {
//        LocalDateTime firstDate;
//        LocalDate localDate;
//        int patternCounter = 0;
//        while (patternCounter < 3) {
//	        try {
//	        	String patterns [] = {"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy-MM-dd'T'HH:mm:ss'Z'", "ddMMyyyy"};
//	        	
//	        	if (patternCounter == 2) {
//	        		
//	        	}
//	        	
//	            firstDate = LocalDateTime.parse(date, DateTimeFormatter.ofPattern(patterns[patternCounter]));
//	
//	            return firstDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
//	        } catch (Exception e) {
//	        	patternCounter ++;
//	            continue;
//	        }
//        }
//        return null;
//    }

    public String parseDate(String date) {
        String[] patterns = {"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy-MM-dd'T'HH:mm:ss'Z'", "ddMMyyyy"};
        for (String pattern : patterns) {
            try {
                return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(pattern))
                                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            } catch (Exception e) {
            	try {
            		return LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern))
            				.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				} catch (Exception ignored) {}
            }
        }
        return null;
    }

    
    @Controller
    class Redirect {
        @GetMapping("/{type}")
        public RedirectView redirectToTypeTransactions(@PathVariable String type) {
        	String redirect = String.format("./api/v1/conciliation/%s/transactions", type);
            return new RedirectView(redirect);
        }

        @GetMapping("/billet")
        public RedirectView redirectToBilletTransactions() {
            return new RedirectView("./api/v1/conciliation/billet/transactions");
        }

    }
}
