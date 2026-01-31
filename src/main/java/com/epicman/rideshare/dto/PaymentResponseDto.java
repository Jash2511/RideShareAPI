package com.epicman.rideshare.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;

public class PaymentResponseDto {

    @Schema(description = "Unique Payment ID")
    private String id;

    @Schema(description = "Ride ID")
    private String rideId;

    @Schema(description = "User ID who made the payment")
    private String userId;

    @Schema(description = "Amount paid")
    private double amount;

    @Schema(description = "Payment Status")
    private String status;

    @Schema(description = "Transaction Reference ID")
    private String transactionId;

    @Schema(description = "Payment Timestamp")
    private Date createdAt;

    public PaymentResponseDto(String id, String rideId, String userId, double amount, String status,
            String transactionId, Date createdAt) {
        this.id = id;
        this.rideId = rideId;
        this.userId = userId;
        this.amount = amount;
        this.status = status;
        this.transactionId = transactionId;
        this.createdAt = createdAt;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getRideId() {
        return rideId;
    }

    public String getUserId() {
        return userId;
    }

    public double getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
