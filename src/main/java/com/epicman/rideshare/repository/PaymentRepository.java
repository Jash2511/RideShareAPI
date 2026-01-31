package com.epicman.rideshare.repository;

import com.epicman.rideshare.model.PaymentModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface PaymentRepository extends MongoRepository<PaymentModel, String> {
    List<PaymentModel> findByRideId(String rideId);

    List<PaymentModel> findByUserId(String userId);
}
