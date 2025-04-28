package com.example.infoplus.domain.payment.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class PaymentResponse {
    private Long totalAmount;
}
