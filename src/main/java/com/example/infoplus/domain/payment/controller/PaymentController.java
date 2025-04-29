package com.example.infoplus.domain.payment.controller;

import com.example.infoplus.domain.payment.dto.request.CommonPaymentRequest;
import com.example.infoplus.domain.payment.service.PaymentService;
import com.example.infoplus.infrastructure.payment.kakao.KakaoPaymentApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    private final KakaoPaymentApiClient kakaoPaymentApiClient;

    @PostMapping("/charge-point")
    public ResponseEntity<?> payment(
            @RequestBody CommonPaymentRequest request
    ) {
        return paymentService.chargePoint(request);
    }

    @PostMapping("/ready")
    public ResponseEntity<?> kakaoPayment(@RequestBody CommonPaymentRequest request) {
        return kakaoPaymentApiClient.paymentReady(request);
    }
}
