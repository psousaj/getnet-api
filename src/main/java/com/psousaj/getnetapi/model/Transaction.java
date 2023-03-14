package com.psousaj.getnetapi.model;

import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonInclude;

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

    @Id
    @Column(name = "payment_id")
    private UUID paymentId;

    @Column(name = "amount")
    private String amount;

    @Column(name = "status")
    private String status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "installments")
    private Integer installments;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "acquirer_transaction_id")
    private String acquirerTransactionId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "authorization_time")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private String authorizationTime;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "brand")
    private String brand;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "terminal_nsu")
    private String nsu;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "transaction_id")
    private String transactionId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "transaction_timestamp")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private String transactionTime;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "authorization_code")
    private String authorizationCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "description_detail")
    private String descriptionDetail;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "error_code")
    private String errorCode;
}
