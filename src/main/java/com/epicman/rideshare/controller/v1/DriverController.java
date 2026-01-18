package com.epicman.rideshare.controller.v1;

import com.epicman.rideshare.model.RideModel;
import com.epicman.rideshare.service.RideService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/driver")
public class DriverController {

	@Autowired
	private RideService rideService;

	@GetMapping("/rides/requests")
	public ResponseEntity<?> getPendingRides(HttpServletRequest request,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "fare") String sortBy,
			@RequestParam(defaultValue = "asc") String direction) {

		String role = (String) request.getAttribute("role");

		if (!role.equals("ROLE_DRIVER")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "You are not a driver"));
		}

		Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<RideModel> rides = rideService.findPendingRides(pageable);

		return ResponseEntity.ok(rides);
	}

	@PostMapping("rides/{id}/accept")
	public ResponseEntity<?> acceptRide(
			@PathVariable("id") String rideId,
			HttpServletRequest request) throws Exception {
		// set by JwtFilter
		String driverId = (String) request.getAttribute("userId");
		String role = (String) request.getAttribute("role");

		if (!role.equals("ROLE_DRIVER")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "You are not a driver"));
		}

		RideModel updatedRide = rideService.acceptRide(rideId, driverId);

		return ResponseEntity.ok(updatedRide);
	}

}
