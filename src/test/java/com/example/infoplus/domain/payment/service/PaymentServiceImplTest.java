package com.example.infoplus.domain.payment.service;

import com.example.infoplus.domain.member.repository.MemberRepository;
import com.example.infoplus.infrastructure.payment.PaymentApiClient;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

class PaymentServiceImplTest {

    @Mock
    PaymentApiClient paymentApiClient;

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    PaymentServiceImpl paymentService;

    @Test
    void chargePoint() {
//        given(paymentApiClient.approvePayment())
    }
}