package com.epicman.rideshare.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PaymentRequestDto {

    @NotBlank(message = "Ride ID is required")
    @Schema(description = "ID of the ride to pay for", example = "65b9...")
    private String rideId;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    @Schema(description = "Amount to pay", example = "25.50")
    private double amount;

    @NotBlank(message = "Payment method is required")
    @Schema(description = "Method of payment (e.g., WALLET, CARD)", example = "WALLET")
    private String paymentMethod;

    public PaymentRequestDto() {
    }

    public PaymentRequestDto(String rideId, double amount, String paymentMethod) {
        this.rideId = rideId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
    }

    public String getRideId() {
        return rideId;
    }

    public void setRideId(String rideId) {
        this.rideId = rideId;
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
}
