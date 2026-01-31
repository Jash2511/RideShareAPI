package com.epicman.rideshare.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "payments")
public class PaymentModel {

    @Id
    private String id;

    private String rideId;
    private String userId;
    private double amount;
    private String paymentMethod; // e.g., "WALLET", "CASH" (mock)
    private String status; // COMPLETED, FAILED
    private String transactionId;
    private Date createdAt;

    public PaymentModel() {
        this.createdAt = new Date();
        this.status = "COMPLETED"; // Default to success for mock
    }

    public PaymentModel(String rideId, String userId, double amount, String paymentMethod) {
        this.rideId = rideId;
        this.userId = userId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = "COMPLETED";
        this.createdAt = new Date();
        this.transactionId = "TXN-" + System.currentTimeMillis(); // Mock ID
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRideId() {
        return rideId;
    }

    public void setRideId(String rideId) {
        this.rideId = rideId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
