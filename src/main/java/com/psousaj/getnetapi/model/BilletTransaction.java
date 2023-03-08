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

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BilletTransaction {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "amount")
    private String amount;

    @Column(name = "status")
    private String status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "order_id")
    private String orderId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "payment_type")
    private String paymentType;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "payment_id")
    private String paymentId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "bank")
    private String bank;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "typeful_line")
    private String typefulLine;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "issue_date")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private String issueDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "expiration_date")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private String expirationDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "payment_date")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private String paymentDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "error_code")
    private String errorCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "description_detail")
    private String descriptionDetail;
}
