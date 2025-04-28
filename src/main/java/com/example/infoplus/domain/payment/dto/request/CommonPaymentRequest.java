package com.example.infoplus.domain.payment.dto.request;

import com.example.infoplus.domain.payment.util.PaymentType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonPaymentRequest {

    private Long memberId;
    private PaymentType paymentType;

    // Toss
    private String paymentKey;
    private String orderId;
    private Long amount;

    // Kakao
    private String itemName;
    private Integer quantity;
    private Integer totalAmount;
    private String partnerOrderId;
    private String cid;
    private String tid;
    private String pgToken;
}

