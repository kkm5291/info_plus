package com.example.infoplus.infrastructure.payment.kakao;

import com.example.infoplus.domain.payment.dto.request.CommonPaymentRequest;
import com.example.infoplus.domain.payment.dto.request.KakaoPaymentApproveRequest;
import com.example.infoplus.domain.payment.dto.request.KakaoPaymentReadyRequest;
import com.example.infoplus.domain.payment.dto.response.CommonPaymentResponse;
import com.example.infoplus.domain.payment.dto.response.KakaoPaymentReadyResponse;
import com.example.infoplus.domain.payment.dto.response.KakaoPaymentResponse;
import com.example.infoplus.domain.payment.util.PaymentType;
import com.example.infoplus.infrastructure.payment.AbstractPaymentApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

@Component
public class KakaoPaymentApiClient extends AbstractPaymentApiClient {

    public static final String AUTHORIZATION_PREFIX = "SECRET_KEY ";

    @Value("${payment.kakao.secret-key}")
    private String secretKey;

    @Value("${payment.kakao.cid}")
    private String cid;

    @Value("${payment.kakao.base-url}")
    private String kakaoPaymentBaseUrl;

    @Value("${payment.kakao.ready-endpoint}")
    private String kakaoReadyEndpoint;

    @Value("${payment.kakao.approve-endpoint}")
    private String kakaoApproveEndpoint;

    @Override
    public CommonPaymentResponse approvePayment(CommonPaymentRequest request) {
        KakaoPaymentApproveRequest approveRequest = KakaoPaymentApproveRequest.builder()
                .cid(cid)
                .tid(request.getTid())
                .partner_order_id(request.getPartnerOrderId())
                .partner_user_id(String.valueOf(request.getMemberId()))
                .pg_token(request.getPgToken())
                .build();

        HttpEntity<KakaoPaymentApproveRequest> httpEntity = new HttpEntity<>(approveRequest, createHeaders());

        KakaoPaymentResponse kakao = callKakaoPayment(httpEntity);

        return CommonPaymentResponse.fromKakao(kakao);
    }

    @Override
    public void setAuthorization(HttpHeaders headers) {
        headers.set(AUTHORIZATION, AUTHORIZATION_PREFIX + secretKey);
    }

    @Override
    public PaymentType getType() {
        return PaymentType.KAKAO;
    }

    public ResponseEntity<?> paymentReady(CommonPaymentRequest request) {
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

        KakaoPaymentReadyResponse readyResponse = restTemplate.postForEntity(kakaoPaymentBaseUrl + kakaoReadyEndpoint,
                httpEntity,
                KakaoPaymentReadyResponse.class).getBody();

        return ResponseEntity.ok(readyResponse);
    }

    private KakaoPaymentResponse callKakaoPayment(HttpEntity<KakaoPaymentApproveRequest> httpEntity) throws RestClientException {
        return restTemplate.postForEntity(
                kakaoPaymentBaseUrl + kakaoApproveEndpoint,
                httpEntity,
                KakaoPaymentResponse.class).getBody();
    }
}
