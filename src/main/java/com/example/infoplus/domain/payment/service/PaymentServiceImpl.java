package com.example.infoplus.domain.payment.service;

import com.example.infoplus.domain.member.entity.Member;
import com.example.infoplus.domain.member.repository.MemberRepository;
import com.example.infoplus.domain.member.service.MemberService;
import com.example.infoplus.domain.payment.dto.request.CommonPaymentRequest;
import com.example.infoplus.domain.payment.dto.response.CommonPaymentResponse;
import com.example.infoplus.domain.payment.entity.Payment;
import com.example.infoplus.domain.payment.repository.PaymentRepository;
import com.example.infoplus.infrastructure.payment.PaymentApiClient;
import com.example.infoplus.infrastructure.payment.PaymentApiClientHandler;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentApiClientHandler paymentApiClientHandler;
    private final MemberService memberService;
    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public ResponseEntity<?> chargePoint(CommonPaymentRequest request) {
        Member findMember = memberService.findById(request.getMemberId());
        PaymentApiClient paymentApiClient = paymentApiClientHandler.getClient(request.getPaymentType());

        CommonPaymentResponse response;
        try {
            response = paymentApiClient.approvePayment(request);
        } catch (RestClientException e) {
            paymentRepository.save(Payment.failFrom(request, findMember));
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }

        memberService.chargePoint(findMember, response.getTotalAmount());
        paymentRepository.save(Payment.successFrom(response, findMember));

        return ResponseEntity.ok(response);
    }
}
