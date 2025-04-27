package com.example.infoplus.domain.payment.dto.request;

import lombok.Getter;
import lombok.Setter;

public class PayRequestDto {

    @Getter
    @Setter
    public static class Toss {
        private String paymentKey;
        private String orderId;
        private Long amount;
    }
}
