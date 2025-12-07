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

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/driver")
public class DriverController {

    @Autowired
    private RideService rideService;

    @GetMapping("/rides/requests")
    public ResponseEntity<?> getPendingRides(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");

        if (!role.equals("ROLE_DRIVER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "You are not a driver"));
        }

        List<RideModel> rides = rideService.findAllRides()
                .stream()
                .filter(ride -> ride.getStatus().equals("REQUESTED"))
                .toList();

        return ResponseEntity.ok(rides);
    }

    @PostMapping("rides/{id}/accept")
    public ResponseEntity<?> acceptRide(
            @PathVariable("id") String rideId,
            HttpServletRequest request
    ) throws Exception {
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