package com.epicman.rideshare.repository;

import com.epicman.rideshare.model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserModel, String> {
    public UserModel findByUsername(String username);
}
