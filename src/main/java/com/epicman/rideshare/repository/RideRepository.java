package com.epicman.rideshare.repository;

import com.epicman.rideshare.model.RideModel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideRepository extends MongoRepository<RideModel, String> {

	// Find all rides created by a passenger
	Page<RideModel> findByUserId(String userId, Pageable pageable);

	// Find all rides assigned to a driver
	Page<RideModel> findByDriverId(String driverId, Pageable pageable);

	// Find rides by status (REQUESTED / ACCEPTED / COMPLETED)
	Page<RideModel> findByStatus(String status, Pageable pageable);

	// Find rides by driver and status
	Page<RideModel> findByDriverIdAndStatus(String driverId, String status, Pageable pageable);
}
