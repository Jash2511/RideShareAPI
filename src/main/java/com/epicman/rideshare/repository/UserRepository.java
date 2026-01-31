package com.epicman.rideshare.repository;

import com.epicman.rideshare.model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserModel, String> {
    public Optional<UserModel> findByUsername(String username);
}
