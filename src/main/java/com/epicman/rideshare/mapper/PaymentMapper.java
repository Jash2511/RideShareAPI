package com.epicman.rideshare.mapper;

import com.epicman.rideshare.dto.PaymentResponseDto;
import com.epicman.rideshare.model.PaymentModel;

public class PaymentMapper {

    public static PaymentResponseDto toResponse(PaymentModel payment) {
        return new PaymentResponseDto(
                payment.getId(),
                payment.getRideId(),
                payment.getUserId(),
                payment.getAmount(),
                payment.getStatus(),
                payment.getTransactionId(),
                payment.getCreatedAt());
    }
}
