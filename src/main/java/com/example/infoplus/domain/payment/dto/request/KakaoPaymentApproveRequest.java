package com.example.infoplus.domain.payment.dto.request;

import lombok.*;

@Builder
@Getter
@Setter
public class KakaoPaymentApproveRequest {
    private String cid;
    private String tid;
    private String partner_order_id;
    private String partner_user_id;
    private String pg_token;
}


