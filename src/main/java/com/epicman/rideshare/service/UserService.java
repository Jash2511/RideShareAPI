package com.epicman.rideshare.service;

import com.epicman.rideshare.exception.ConflictException;
import com.epicman.rideshare.model.UserModel;
import com.epicman.rideshare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

import com.epicman.rideshare.exception.NotFoundException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CloudinaryService cloudinaryService;

    @CachePut(value = "users", key = "#result.username")
    public UserModel create(UserModel userModel) {
        return userRepository.save(userModel);
    }

    @Cacheable(value = "users", key = "#username")
    public UserModel findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found: " + username));
    }

    public UserModel findById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

    @CachePut(value = "users", key = "#result.username")
    public UserModel registerUser(String username, String password, String role,
            MultipartFile file) throws ConflictException, IOException {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new ConflictException("A user with that username already exists");
        }

        UserModel userModel = new UserModel();
        userModel.setUsername(username);
        userModel.setPasswordHash(passwordEncoder.encode(password));
        userModel.setRole(role);

        if (file != null && !file.isEmpty()) {
            String imageUrl = cloudinaryService.uploadImage(file);
            userModel.setProfilePictureUrl(imageUrl);
        }

        return userRepository.save(userModel);
    }

    @CachePut(value = "users", key = "#result.username")
    public UserModel updateProfilePicture(String userId, MultipartFile file)
            throws IOException {
        UserModel userModel = findById(userId);
        // findById now throws NotFoundException if null, so no need to check here
        String imageUrl = cloudinaryService.uploadImage(file);
        userModel.setProfilePictureUrl(imageUrl);
        return userRepository.save(userModel);
    }
}
