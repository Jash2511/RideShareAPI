package com.epicman.rideshare.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "rides")
public class RideModel {

    @Id
    private String id;

    private String userId; // Passenger
    private String driverId; // Driver (nullable)
    private String pickupLocation;
    private String dropLocation;
    private String status; // REQUESTED / ACCEPTED / COMPLETED
    private Date createdAt;

    private double fare;
    private String paymentStatus; // PENDING / PAID

    // Constructors
    public RideModel() {
        this.createdAt = new Date();
        this.status = "REQUESTED";
        this.paymentStatus = "PENDING";
    }

    public RideModel(String userId, String pickupLocation, String dropLocation, double fare) {
        this.userId = userId;
        this.pickupLocation = pickupLocation;
        this.dropLocation = dropLocation;
        this.status = "REQUESTED";
        this.paymentStatus = "PENDING";
        this.createdAt = new Date();
        this.fare = fare;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getDropLocation() {
        return dropLocation;
    }

    public void setDropLocation(String dropLocation) {
        this.dropLocation = dropLocation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
