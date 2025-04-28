package com.example.infoplus.domain.payment.service;

import com.example.infoplus.domain.member.entity.Member;
import com.example.infoplus.domain.member.repository.MemberRepository;
import com.example.infoplus.domain.payment.dto.request.CommonPaymentRequest;
import com.example.infoplus.domain.payment.dto.request.PaymentRequest;
import com.example.infoplus.domain.payment.dto.request.TossPaymentRequest;
import com.example.infoplus.domain.payment.dto.response.PaymentResponse;
import com.example.infoplus.domain.payment.repository.PaymentRepository;
import com.example.infoplus.infrastructure.payment.PaymentApiClient;
import com.example.infoplus.infrastructure.payment.kakao.KakaoPaymentApiClient;
import com.example.infoplus.infrastructure.payment.toss.TossPaymentApiClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final TossPaymentApiClient tossPaymentApiClient;
    private final KakaoPaymentApiClient kakaoPaymentApiClient;
    private final MemberRepository memberRepository;
    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public ResponseEntity<?> chargePoint(CommonPaymentRequest request) {
        Member findMember = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다"));

        PaymentResponse toss = tossPaymentApiClient.approvePayment(request);
        findMember.convertAmountToPoint(toss.getTotalAmount());
        return ResponseEntity.ok().build();
    }
}
