package com.example.infoplus.domain.payment.dto.response;

import com.example.infoplus.domain.payment.util.PaymentStatusType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommonPaymentResponse {

    private String paymentId;
    private String orderId;
    private Long totalAmount;
    private PaymentStatusType status;
    private String approvedAt;

    public static CommonPaymentResponse fromKakao(KakaoPaymentResponse response) {
        return new CommonPaymentResponse(
                response.getTid(),
                response.getPartnerOrderId(),
                response.getAmount().getTotal(),
                PaymentStatusType.DONE,
                LocalDateTime.now().toString()
        );
    }

    public static CommonPaymentResponse fromToss(TossPaymentResponse response) {
        return new CommonPaymentResponse(
                response.getPaymentKey(),
                response.getOrderId(),
                response.getTotalAmount(),
                PaymentStatusType.DONE,
                response.getApprovedAt()
        );
    }
}
