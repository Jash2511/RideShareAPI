package com.epicman.rideshare.controller;

import com.epicman.rideshare.dto.UserLoginRequestDto;
import com.epicman.rideshare.dto.UserRegisterRequestDto;
import com.epicman.rideshare.exception.NotFoundException;
import com.epicman.rideshare.mapper.UserMapper;
import com.epicman.rideshare.model.UserModel;
import com.epicman.rideshare.service.UserService;
import com.epicman.rideshare.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterRequestDto userRequestDto) throws Exception {
        UserModel createdUser = userService.registerUser(userRequestDto.getUsername(), userRequestDto.getPassword(), userRequestDto.getRole());

        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toResponse(createdUser));
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody UserLoginRequestDto userLoginRequestDto) throws Exception {
        UserModel user = userService.findByUsername(userLoginRequestDto.getUsername());

        if (user == null) {
            throw new NotFoundException("User could not be found");
        }

        return jwtUtil.generateToken(user.getUsername(), user.getRole());
    }
}
