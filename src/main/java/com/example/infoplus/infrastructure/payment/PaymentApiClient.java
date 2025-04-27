package com.example.infoplus.infrastructure.payment;

import com.example.infoplus.domain.payment.dto.request.PayRequestDto;
import com.example.infoplus.domain.payment.dto.response.PayResponseDto;
import org.springframework.http.ResponseEntity;

public interface PaymentApiClient {
    PayResponseDto.Toss approvePayment(PayRequestDto.Toss payRequestDto);
}
