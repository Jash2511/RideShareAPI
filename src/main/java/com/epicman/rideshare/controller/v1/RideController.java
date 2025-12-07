package com.epicman.rideshare.controller.v1;

import com.epicman.rideshare.dto.RideRequestDto;
import com.epicman.rideshare.model.RideModel;
import com.epicman.rideshare.service.RideService;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/rides")
public class RideController {

    @Autowired
    private RideService rideService;

    @PostMapping
    public ResponseEntity<RideModel> createRide(
            @Valid @RequestBody RideRequestDto rideRequestDto,
            HttpServletRequest request
    ) {
        // userId should be set by JwtFilter
        String userId = (String) request.getAttribute("userId");

        RideModel ride = rideService.create(userId, rideRequestDto);
        return new ResponseEntity<>(ride, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<?> completeRide(
            @PathVariable("id") String rideId,
            HttpServletRequest request
    ) throws Exception {
        String userId = (String) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");

        if (!role.equals("ROLE_DRIVER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "You are not a driver"));
        }

        RideModel completedRide = rideService.completeRide(rideId, userId);

        return ResponseEntity.ok(completedRide);
    }
}
