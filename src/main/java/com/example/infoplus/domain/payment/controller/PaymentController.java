package com.example.infoplus.domain.payment.controller;

import com.example.infoplus.domain.payment.dto.request.KakaoPaymentRequest;
import com.example.infoplus.domain.payment.dto.request.TossPaymentRequest;
import com.example.infoplus.domain.payment.service.PaymentService;
import com.example.infoplus.infrastructure.payment.kakao.KakaoPaymentApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    private final KakaoPaymentApiClient kakaoPaymentApiClient;

    @PostMapping("/charge-point/toss")
    public ResponseEntity<?> payment(
            @RequestBody TossPaymentRequest paymentRequest
    ) {
        return paymentService.chargePoint(paymentRequest);
    }

    @PostMapping("/charge-point/kakao")
    public ResponseEntity<?> payment(
            @RequestBody KakaoPaymentRequest paymentRequest
    ) {
        return paymentService.chargePoint(paymentRequest);
    }

    @PostMapping("/ready")
    public ResponseEntity<?> kakaoPayment(@RequestBody KakaoPaymentRequest request) {
        return kakaoPaymentApiClient.paymentReady(request);
    }

    @GetMapping("/toss/get")
    public ResponseEntity<?> paymentTest(
            @RequestParam("paymentKey") String paymentKey,
            @RequestParam("orderId") String orderId,
            @RequestParam("amount") long amount

    ) {
        TossPaymentRequest paymentRequest = new TossPaymentRequest();
        paymentRequest.setPaymentKey(paymentKey);
        paymentRequest.setOrderId(orderId);
        paymentRequest.setAmount(amount);
        paymentRequest.setMemberId(1L);

        return payment(paymentRequest);
    }
}
