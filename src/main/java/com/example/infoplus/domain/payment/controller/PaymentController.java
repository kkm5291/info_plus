package com.example.infoplus.domain.payment.controller;

import com.example.infoplus.domain.payment.dto.request.PaymentRequest;
import com.example.infoplus.domain.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/charge-point")
    public ResponseEntity<?> payment(
            @RequestBody PaymentRequest.Toss paymentRequestDto
    ) {
        return paymentService.chargePoint(paymentRequestDto);
    }

    @GetMapping("/toss/get")
    public ResponseEntity<?> paymentTest(
            @RequestParam("paymentKey") String paymentKey,
            @RequestParam("orderId") String orderId,
            @RequestParam("amount") long amount

    ) {
        PaymentRequest.Toss paymentRequestDto = new PaymentRequest.Toss();
        paymentRequestDto.setPaymentKey(paymentKey);
        paymentRequestDto.setOrderId(orderId);
        paymentRequestDto.setAmount(amount);
        paymentRequestDto.setMemberId(1L);

        return payment(paymentRequestDto);
    }
}
