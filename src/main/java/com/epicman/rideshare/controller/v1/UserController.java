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

import com.epicman.rideshare.service.UserService;
import com.epicman.rideshare.model.UserModel;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	@Autowired
	private RideService rideService;

	@Autowired
	private UserService userService;

	@PostMapping(value = "/profile-picture", consumes = "multipart/form-data")
	public ResponseEntity<?> uploadProfilePicture(
			HttpServletRequest request,
			@RequestParam("file") MultipartFile file) throws IOException {

		String userId = (String) request.getAttribute("userId");

		// Assuming 'userId' attribute is set by authentication filter and is the
		// database ID
		UserModel updatedUser = userService.updateProfilePicture(userId, file);
		return ResponseEntity.ok(updatedUser);
	}

	@GetMapping("/rides")
	public ResponseEntity<?> getPendingRides(
			HttpServletRequest request,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "fare") String sortBy,
			@RequestParam(defaultValue = "asc") String direction) {

		String userId = (String) request.getAttribute("userId");
		String role = (String) request.getAttribute("role");

		if (!role.equals("ROLE_USER")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "You are not a normal user"));
		}

		Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<RideModel> rides = rideService.findRidesByUserId(userId, pageable);

		return ResponseEntity.ok(rides);
	}

}
