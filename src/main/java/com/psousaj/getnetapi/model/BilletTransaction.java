package com.psousaj.getnetapi.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class BilletTransaction{
    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "customer_id")
    private String customerId;

    @Id @Column(name = "id")
    private String id;

    @Column(name = "amount")
    private String amount;

    @Column(name = "status")
    private String status;

    @Column(name = "typeful_line")
    private String typefulLine;

    @Column(name = "issue_date") @DateTimeFormat(pattern = "ddMMyyy")
    private Date issueDate;

    @Column(name = "expiration_date") @DateTimeFormat(pattern = "ddMMyyy")
    private Date expirationDate;
}
