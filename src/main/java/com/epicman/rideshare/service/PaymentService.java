package com.epicman.rideshare.service;

import com.epicman.rideshare.dto.PaymentRequestDto;
import com.epicman.rideshare.dto.PaymentResponseDto;
import com.epicman.rideshare.exception.BadRequestException;
import com.epicman.rideshare.exception.NotFoundException;
import com.epicman.rideshare.mapper.PaymentMapper;
import com.epicman.rideshare.model.PaymentModel;
import com.epicman.rideshare.model.RideModel;
import com.epicman.rideshare.repository.PaymentRepository;
import com.epicman.rideshare.repository.RideRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final RideRepository rideRepository;

    public PaymentService(PaymentRepository paymentRepository, RideRepository rideRepository) {
        this.paymentRepository = paymentRepository;
        this.rideRepository = rideRepository;
    }

    @Transactional
    @CacheEvict(value = "user_payments", key = "#userId")
    public PaymentResponseDto processPayment(String userId, PaymentRequestDto request) throws BadRequestException {
        RideModel ride = rideRepository.findById(request.getRideId())
                .orElseThrow(() -> new NotFoundException("Ride not found"));

        if (!ride.getUserId().equals(userId)) {
            throw new BadRequestException("Ride does not belong to user");
        }

        if ("PAID".equals(ride.getPaymentStatus())) {
            throw new BadRequestException("Ride is already paid");
        }

        // Create Payment
        PaymentModel payment = new PaymentModel(
                request.getRideId(),
                userId,
                request.getAmount(),
                request.getPaymentMethod());
        PaymentModel savedPayment = paymentRepository.save(payment);

        // Update Ride Status
        ride.setPaymentStatus("PAID");
        rideRepository.save(ride);

        return PaymentMapper.toResponse(savedPayment);
    }

    @Cacheable(value = "user_payments", key = "#userId")
    public List<PaymentResponseDto> getPaymentHistory(String userId) {
        return paymentRepository.findByUserId(userId).stream()
                .map(PaymentMapper::toResponse)
                .toList();
    }
}
