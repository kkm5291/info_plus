package com.example.infoplus.infrastructure.payment.kakao;

import com.example.infoplus.domain.payment.dto.request.KakaoPaymentApproveRequest;
import com.example.infoplus.domain.payment.dto.request.KakaoPaymentReadyRequest;
import com.example.infoplus.domain.payment.dto.request.KakaoPaymentRequest;
import com.example.infoplus.domain.payment.dto.response.KakaoPaymentReadyResponse;
import com.example.infoplus.domain.payment.dto.response.KakaoPaymentResponse;
import com.example.infoplus.domain.payment.dto.response.PaymentResponse;
import com.example.infoplus.infrastructure.payment.AbstractPaymentApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class KakaoPaymentApiClient extends AbstractPaymentApiClient<KakaoPaymentRequest> {

    public static final String AUTHORIZATION_PREFIX = "SECRET_KEY ";

    @Value("${payment.kakao.secret-key}")
    private String secretKey;

    @Value("${payment.kakao.cid}")
    private String cid;

    @Override
    public PaymentResponse approvePayment(KakaoPaymentRequest paymentRequest) {
        KakaoPaymentApproveRequest approveRequest = KakaoPaymentApproveRequest.builder()
                .cid(cid)
                .tid(paymentRequest.getTid())
                .partner_order_id(paymentRequest.getPartnerOrderId())
                .partner_user_id(String.valueOf(paymentRequest.getMemberId()))
                .pg_token(paymentRequest.getPgToken())
                .build();

        HttpEntity<KakaoPaymentApproveRequest> httpEntity = new HttpEntity<>(approveRequest, createHeaders());

        return restTemplate.postForEntity(
                "https://open-api.kakaopay.com/online/v1/payment/approve",
                httpEntity,
                KakaoPaymentResponse.class).getBody();
    }

    @Override
    public void setAuthorization(HttpHeaders headers) {
        headers.set(AUTHORIZATION, AUTHORIZATION_PREFIX + secretKey);
    }

    public ResponseEntity<?> paymentReady(KakaoPaymentRequest request) {
        KakaoPaymentReadyRequest readyRequest = KakaoPaymentReadyRequest.builder()
                .cid(cid)
                .partner_order_id(request.getPartnerOrderId())
                .partner_user_id(String.valueOf(request.getMemberId()))
                .item_name(request.getItemName())
                .quantity(request.getQuantity())
                .total_amount(request.getTotalAmount())
                .tax_free_amount(0)
                .approval_url("http://localhost:8080/payment/kakao/approve")
                .cancel_url("http://localhost:8080/payment/kakao/cancel")
                .fail_url("http://localhost:8080/payment/kakao/fail")
                .build();

        HttpEntity<KakaoPaymentReadyRequest> httpEntity = new HttpEntity<>(readyRequest, createHeaders());

        return restTemplate.postForEntity("https://open-api.kakaopay.com/online/v1/payment/ready",
                httpEntity,
                KakaoPaymentReadyResponse.class);
    }
}
