package com.example.infoplus.domain.payment.service;

import com.example.infoplus.domain.payment.dto.request.CommonPaymentRequest;
import org.springframework.http.ResponseEntity;

public interface PaymentService {
    ResponseEntity<?> chargePoint(CommonPaymentRequest request);
}
