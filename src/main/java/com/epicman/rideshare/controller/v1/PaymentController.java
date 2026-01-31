package com.epicman.rideshare.controller.v1;

import com.epicman.rideshare.dto.PaymentRequestDto;
import com.epicman.rideshare.dto.PaymentResponseDto;
import com.epicman.rideshare.exception.BadRequestException;
import com.epicman.rideshare.model.UserModel;
import com.epicman.rideshare.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@Tag(name = "Payment Management", description = "Internal payment processing endpoints")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    @Operation(summary = "Process a payment", description = "Mock payment processing for a ride")
    public ResponseEntity<PaymentResponseDto> processPayment(
            @AuthenticationPrincipal UserModel user,
            @Valid @RequestBody PaymentRequestDto request) throws BadRequestException {

        return ResponseEntity.ok(paymentService.processPayment(user.getId(), request));
    }

    @GetMapping("/my")
    @Operation(summary = "Get payment history", description = "Retrieve payment history for the current user")
    public ResponseEntity<List<PaymentResponseDto>> getMyPayments(
            @AuthenticationPrincipal UserModel user) {

        return ResponseEntity.ok(paymentService.getPaymentHistory(user.getId()));
    }
}
