package com.epicman.rideshare.repository;

import com.epicman.rideshare.model.RideModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RideRepository extends MongoRepository<RideModel, String> {

    // Find all rides created by a passenger
    List<RideModel> findByUserId(String userId);

    // Find all rides assigned to a driver
    List<RideModel> findByDriverId(String driverId);

    // Find rides by status (REQUESTED / ACCEPTED / COMPLETED)
    List<RideModel> findByStatus(String status);

    // Find rides by driver and status
    List<RideModel> findByDriverIdAndStatus(String driverId, String status);
}
