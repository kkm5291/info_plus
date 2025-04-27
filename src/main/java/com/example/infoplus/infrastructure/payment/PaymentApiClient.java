package com.example.infoplus.infrastructure.payment;


import com.example.infoplus.domain.payment.dto.request.PaymentRequest;
import com.example.infoplus.domain.payment.dto.response.PaymentResponse;

public interface PaymentApiClient {
    PaymentResponse.Toss approvePayment(PaymentRequest.Toss payRequestDto);
}
