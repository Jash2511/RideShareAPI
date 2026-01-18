package com.epicman.rideshare.service;

import com.epicman.rideshare.dto.RideRequestDto;
import com.epicman.rideshare.exception.BadRequestException;
import com.epicman.rideshare.exception.ForbiddenException;
import com.epicman.rideshare.exception.NotFoundException;
import com.epicman.rideshare.model.RideModel;
import com.epicman.rideshare.repository.RideRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RideService {

	private final RideRepository rideRepository;

	public RideService(RideRepository rideRepository) {
		this.rideRepository = rideRepository;
	}

	public RideModel create(String userId, RideRequestDto dto) {
		RideModel ride = new RideModel();
		ride.setUserId(userId);
		ride.setPickupLocation(dto.getPickupLocation());
		ride.setDropLocation(dto.getDropLocation());
		ride.setStatus("REQUESTED");
		ride.setFare(dto.getFare());

		return rideRepository.save(ride);
	}

	public Page<RideModel> findRidesByUserId(String userId, Pageable pageable) {
		return rideRepository.findByUserId(userId, pageable);
	}

	public Page<RideModel> findAllRides(Pageable pageable) {
		return rideRepository.findAll(pageable);
	}

	public Page<RideModel> findPendingRides(Pageable pageable) {
		return rideRepository.findByStatus("REQUESTED", pageable);
	}

	public RideModel acceptRide(String rideId, String driverId) throws NotFoundException, BadRequestException {
		RideModel ride = rideRepository.findById(rideId)
				.orElseThrow(() -> new NotFoundException("Ride not found"));

		if (!ride.getStatus().equals("REQUESTED")) {
			throw new BadRequestException("Ride cannot be accepted");
		}

		ride.setStatus("ACCEPTED");
		ride.setDriverId(driverId);

		return rideRepository.save(ride);
	}

	public RideModel completeRide(String rideId, String driverId) throws ForbiddenException, BadRequestException {
		RideModel ride = rideRepository.findById(rideId)
				.orElseThrow(() -> new NotFoundException("Ride not found"));

		if (!ride.getDriverId().equals(driverId)) {
			throw new ForbiddenException("Not your ride");
		}

		if (!ride.getStatus().equals("ACCEPTED")) {
			throw new BadRequestException("Ride is not yet accepted");
		}

		ride.setStatus("COMPLETED");

		return rideRepository.save(ride);
	}

}
