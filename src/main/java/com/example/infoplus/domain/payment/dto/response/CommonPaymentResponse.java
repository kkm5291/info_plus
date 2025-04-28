package com.example.infoplus.domain.payment.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonPaymentResponse {

    private String paymentId;
    private String orderId;
    private Long totalAmount;
    private String status;
    private String approvedAt;
}
