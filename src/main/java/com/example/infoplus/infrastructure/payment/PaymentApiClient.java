package com.example.infoplus.infrastructure.payment;


import com.example.infoplus.domain.payment.dto.request.CommonPaymentRequest;
import com.example.infoplus.domain.payment.dto.response.PaymentResponse;

public interface PaymentApiClient {
    PaymentResponse approvePayment(CommonPaymentRequest request);
}
