package com.example.infoplus.domain.payment.dto.request;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public abstract class PaymentRequest {

    private Long memberId;
}
