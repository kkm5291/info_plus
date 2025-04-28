package com.example.infoplus.domain.payment.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TossPaymentRequest extends PaymentRequest {
    private String paymentKey;
    private String orderId;
    private Long amount;

}
