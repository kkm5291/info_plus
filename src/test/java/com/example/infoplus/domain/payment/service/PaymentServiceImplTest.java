package com.example.infoplus.domain.payment.service;

import com.example.infoplus.domain.member.entity.Member;
import com.example.infoplus.domain.member.service.MemberService;
import com.example.infoplus.domain.payment.dto.request.CommonPaymentRequest;
import com.example.infoplus.domain.payment.dto.response.CommonPaymentResponse;
import com.example.infoplus.domain.payment.entity.Payment;
import com.example.infoplus.domain.payment.repository.PaymentRepository;
import com.example.infoplus.domain.payment.util.PaymentStatusType;
import com.example.infoplus.domain.payment.util.PaymentType;
import com.example.infoplus.infrastructure.payment.PaymentApiClient;
import com.example.infoplus.infrastructure.payment.PaymentApiClientHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.*;

class PaymentServiceImplTest {

    @Mock
    PaymentApiClientHandler paymentApiClientHandler;

    @Mock
    MemberService memberService;

    @Mock
    PaymentRepository paymentRepository;

    @InjectMocks
    PaymentServiceImpl paymentService;

    Member member;

    CommonPaymentRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        member = Member.of("KIM");

        request = new CommonPaymentRequest();
        request.setMemberId(1L);
        request.setPaymentType(PaymentType.KAKAO);
        request.setPaymentKey("12345");
        request.setTotalAmount(1000L);
    }

    @Test
    @DisplayName("정상 요청, 정상 처리")
    void chargePoint() {
        CommonPaymentResponse response = new CommonPaymentResponse(
                "12345",
                "order-001",
                1000L,
                PaymentStatusType.DONE,
                LocalDateTime.now().toString());

        PaymentApiClient paymentApiClient = mock(PaymentApiClient.class);

        given(memberService.findById(1L)).willReturn(member);
        given(paymentApiClientHandler.getClient(PaymentType.KAKAO)).willReturn(paymentApiClient);
        given(paymentApiClient.approvePayment(request)).willReturn(response);

        ResponseEntity<?> result = paymentService.chargePoint(request);

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(paymentRepository).save(any(Payment.class));
        verify(memberService).chargePoint(member, 1000L);

        verify(paymentRepository).save(argThat(payment -> {
            assertThat(payment.getPaymentKey()).isEqualTo(response.getPaymentId());
            assertThat(payment.getOrderId()).isEqualTo(response.getOrderId());
            assertThat(payment.getTotalAmount()).isEqualTo(response.getTotalAmount());
            assertThat(payment.getStatus()).isEqualTo(PaymentStatusType.DONE);
            return true;
        }));
    }

    @Test
    @DisplayName("Member가 존재하지 않는 경우 결제 취소")
    void chargePoint_memberNotFound_ERROR() {
        given(memberService.findById(1L)).willThrow(new IllegalArgumentException("존재하지 않는 사용자 입니다."));
        verify(paymentApiClientHandler, never()).getClient(any());
    }

    @Test
    @DisplayName("지원하지 않는 결제 타입인 경우 결제 취소")
    void chargePoint_notSupportedPaymentType_ERROR() {
        request.setPaymentType(null);
        given(paymentApiClientHandler.getClient(any())).willThrow(new IllegalArgumentException("지원하지 않는 결제 타입입니다"));

        assertThatThrownBy(() -> paymentService.chargePoint(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("지원하지 않는 결제 타입입니다");

        verify(memberService, never()).chargePoint(any(), anyLong());
        verifyNoInteractions(paymentRepository);
    }

    @Test
    @DisplayName("third party API의 응답 예외 발생 시 결제 취소")
    void chargePoint_paymentApprovalFails_ERROR() {
        PaymentApiClient paymentApiClient = mock(PaymentApiClient.class);

        given(memberService.findById(1L)).willReturn(member);
        given(paymentApiClientHandler.getClient(PaymentType.KAKAO)).willReturn(paymentApiClient);
        willThrow(new RestClientException("Payment failed")).given(paymentApiClient).approvePayment(request);

        ResponseEntity<?> result = paymentService.chargePoint(request);

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        verify(paymentRepository).save(any(Payment.class));
        verify(memberService, never()).chargePoint(any(), anyLong());
    }

    @Test
    @DisplayName("결제 요청 실패 시 Request 객체 데이터 토대로 Payment 저장")
    void chargePoint_saveFailedPaymentEntity() {
        PaymentApiClient paymentApiClient = mock(PaymentApiClient.class);

        given(memberService.findById(request.getMemberId())).willReturn(member);
        given(paymentApiClientHandler.getClient(request.getPaymentType())).willReturn(paymentApiClient);
        willThrow(new RestClientException("Payment failed")).given(paymentApiClient).approvePayment(request);

        paymentService.chargePoint(request);

        verify(paymentRepository).save(argThat(payment -> {
            assertThat(payment.getPaymentKey()).isEqualTo(request.getPaymentKey());
            assertThat(payment.getOrderId()).isEqualTo(request.getOrderId());
            assertThat(payment.getTotalAmount()).isEqualTo(request.getTotalAmount());
            assertThat(payment.getStatus()).isEqualTo(PaymentStatusType.FAIL);
            return true;
        }));
    }

    @AfterEach
    void tearDown() {
        member = null;
        request = null;
    }
}