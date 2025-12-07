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
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private RideService rideService;

    @GetMapping("/rides")
    public ResponseEntity<?> getPendingRides(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");

        if (!role.equals("ROLE_USER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "You are not a normal user"));
        }

        List<RideModel> rides = rideService.findRidesByUserId(userId)
                .stream()
                .filter(ride -> ride.getStatus().equals("REQUESTED"))
                .toList();

        return ResponseEntity.ok(rides);
    }


}