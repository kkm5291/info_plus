package com.example.infoplus.domain.payment.dto.response;

import lombok.Getter;
import lombok.Setter;

public class PayResponseDto {

    @Getter
    @Setter
    public static class Toss {
        private String paymentKey;
        private String orderId;
        private int totalAmount;
        private String status;
        private String approvedAt;
    }
}
