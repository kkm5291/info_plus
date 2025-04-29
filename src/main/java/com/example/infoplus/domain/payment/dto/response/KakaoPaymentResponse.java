package com.example.infoplus.domain.payment.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoPaymentResponse {
    private String aid;
    private String tid;
    private String cid;
    private String partnerOrderId;
    private String partnerUserId;
    private String paymentMethodType;

    private Amount amount;

    @Getter
    @Setter
    public static class Amount {
        private Long total;
        private Long taxFree;
        private Long vat;
        private Long discount;
    }
}
