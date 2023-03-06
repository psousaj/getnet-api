package com.psousaj.getnetapi.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "payment_id")
    private UUID paymentId;

    @Column(name = "amount")
    private String amount;

    @Column(name = "status")
    private String status;

    @Column(name = "installments")
    private Integer installments;

    @Column(name = "acquirer_transaction_id")
    private String acquirerTransactionId;

    @Column(name = "authorization_time")
    private String authorizationTime;

    @Column(name = "brand")
    private String brand;

    @Id @Column(name = "terminal_nsu")
    private String terminalNsu;

    @Column(name = "authorization_code")
    private String authorizationCode;
}
