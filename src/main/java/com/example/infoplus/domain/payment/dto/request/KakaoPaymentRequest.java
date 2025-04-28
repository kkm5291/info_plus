package com.example.infoplus.domain.payment.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoPaymentRequest {
    private String itemName;
    private Integer quantity;
    private Integer totalAmount;
    private String partnerOrderId;

    private String cid;
    private String tid;
    private String pgToken;
}
