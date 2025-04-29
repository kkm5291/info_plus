package com.example.infoplus.domain.payment.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TossPaymentResponse {
    private Long totalAmount;
    private String paymentKey;
    private String orderId;
    private String status;
    private String approvedAt;
}
