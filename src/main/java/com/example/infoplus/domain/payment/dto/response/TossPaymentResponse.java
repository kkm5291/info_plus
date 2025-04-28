package com.example.infoplus.domain.payment.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
public class TossPaymentResponse extends PaymentResponse {
    private String paymentKey;
    private String orderId;
    private String status;
    private String approvedAt;
}
