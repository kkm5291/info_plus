package com.example.infoplus.domain.payment.service;

import com.example.infoplus.domain.payment.dto.request.PaymentRequest;
import org.springframework.http.ResponseEntity;

public interface PaymentService {
    ResponseEntity<?> chargePoint(PaymentRequest.Toss payRequestDto);
}
