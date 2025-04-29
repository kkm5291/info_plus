package com.example.infoplus.domain.payment.entity;

import com.example.infoplus.domain.member.entity.Member;
import com.example.infoplus.domain.payment.dto.request.CommonPaymentRequest;
import com.example.infoplus.domain.payment.dto.response.CommonPaymentResponse;
import com.example.infoplus.domain.payment.util.PaymentStatusType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String paymentKey;

    private String orderId;

    private Long totalAmount;

    private PaymentStatusType status;

    private String approvedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public static Payment successFrom(CommonPaymentResponse response, Member member) {
        Payment payment = new Payment();
        payment.paymentKey = response.getPaymentId();
        payment.orderId = response.getOrderId();
        payment.totalAmount = response.getTotalAmount();
        payment.status = PaymentStatusType.DONE;
        payment.approvedAt = response.getApprovedAt();
        payment.member = member;
        return payment;
    }

    public static Payment failFrom(CommonPaymentRequest request, Member member) {
        Payment payment = new Payment();
        payment.paymentKey = request.getPaymentKey();
        payment.orderId = request.getOrderId();
        payment.totalAmount = Optional.ofNullable(request.getAmount())
                .orElse(request.getTotalAmount());
        payment.status = PaymentStatusType.FAIL;
        payment.approvedAt = null;
        payment.member = member;
        return payment;
    }
}
