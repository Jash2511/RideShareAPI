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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping(value = "/register", consumes = "multipart/form-data")
    public ResponseEntity<?> register(
            @Valid @ModelAttribute UserRegisterRequestDto userRequestDto,
            @RequestParam(value = "file", required = false) MultipartFile file)
            throws Exception {

        UserModel createdUser = userService.registerUser(
                userRequestDto.getUsername(),
                userRequestDto.getPassword(),
                userRequestDto.getRole(),
                file);

        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toResponse(createdUser));
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody UserLoginRequestDto userLoginRequestDto) throws Exception {
        UserModel user = userService.findByUsername(userLoginRequestDto.getUsername());
        return jwtUtil.generateToken(user.getUsername(), user.getRole());
    }
}
