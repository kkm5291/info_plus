//package com.example.infoplus.infrastructure.payment.kakao;
//
//import com.example.infoplus.domain.payment.dto.request.PaymentRequestDto;
//import com.example.infoplus.domain.payment.dto.response.PaymentResponseDto;
//import com.example.infoplus.infrastructure.payment.PaymentApiClient;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//@Component
//public class KakaoPaymentApiClient implements PaymentApiClient {
//
//    @Value("${payment.kakao.secret-key}")
//    private String secretKey;
//
//    @Value("${payment.kakao.cid}")
//    private String cid;
//
//    @Override
//    public PaymentResponseDto.Toss approvePayment(PaymentRequestDto.KakaoReady payRequestDto) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("Authorization", "SECRET_KEY " + secretKey);
//
//        HttpEntity<PaymentRequestDto.KakaoReady> httpEntity = new HttpEntity<>(PaymentRequestDto.KakaoReady, headers);
//
//        RestTemplate restTemplate = new RestTemplate();
//        return null;
//    }
//}
