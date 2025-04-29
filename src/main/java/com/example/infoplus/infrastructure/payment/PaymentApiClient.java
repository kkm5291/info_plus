package com.example.infoplus.infrastructure.payment;

import com.example.infoplus.domain.payment.dto.request.CommonPaymentRequest;
import com.example.infoplus.domain.payment.dto.response.CommonPaymentResponse;
import com.example.infoplus.domain.payment.util.PaymentType;

public interface PaymentApiClient {
    CommonPaymentResponse approvePayment(CommonPaymentRequest request);

    PaymentType getType();
}
