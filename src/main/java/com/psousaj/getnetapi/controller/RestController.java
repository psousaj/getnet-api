package com.psousaj.getnetapi.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.psousaj.getnetapi.model.Transaction;
import com.psousaj.getnetapi.services.TransactionsService;

import lombok.var;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {
    
    @Autowired
    private TransactionsService service;

    @GetMapping()
    public ModelAndView welcome(){
        return new ModelAndView("index");
    }

    @GetMapping("/credit")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Transaction> credit(@RequestParam(required=true) String payment_type, 
                                            @RequestParam(required=true) String customer_id, 
                                            @RequestParam(required=true) String order_id,
                                            @RequestParam(required=true) UUID payment_id,
                                            @RequestParam(required=true) Integer amount,
                                            @RequestParam(required=true) String status,
                                            @RequestParam(required=true) Integer number_installments,
                                            @RequestParam(required=true) String acquirer_transaction_id,
                                            @RequestParam(required=true) String authorization_timestamp,
                                            @RequestParam(required=true) String brand,
                                            @RequestParam(required=true) String terminal_nsu,
                                            @RequestParam(required=true) String authorization_code){
        
        var actualTransaction = Transaction.builder().paymentType(payment_type)
                                                        .paymentId(payment_id)
                                                        .customerId(authorization_code)
                                                        .orderId(order_id)
                                                        .amount(authorization_code)
                                                        .installments(number_installments)
                                                        .acquirerTransactionId(acquirer_transaction_id)
                                                        .authorizationTime(authorization_timestamp)
                                                        .brand(brand)
                                                        .terminalNsu(terminal_nsu)
                                                        .authorizationCode(authorization_code)
                                                        .build();
                                                        
        return service.saveTransaction(actualTransaction);
    }
}

