package com.example.infoplus.infrastructure.payment.toss;

import com.example.infoplus.domain.payment.dto.request.CommonPaymentRequest;
import com.example.infoplus.domain.payment.dto.response.CommonPaymentResponse;
import com.example.infoplus.domain.payment.dto.response.TossPaymentResponse;
import com.example.infoplus.domain.payment.util.PaymentType;
import com.example.infoplus.infrastructure.payment.AbstractPaymentApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class TossPaymentApiClient extends AbstractPaymentApiClient {

    public static final String AUTHORIZATION_PREFIX = "Basic ";
    public static final String COLON_SEPARATOR = ":";

    @Value("${payment.toss.secret-key}")
    private String secretKey;

    @Value("${payment.toss.base-url}")
    private String tossPaymentBaseUrl;

    @Value("${payment.toss.confirm-endpoint}")
    private String tossConfirmEndpoint;

    @Override
    public CommonPaymentResponse approvePayment(CommonPaymentRequest request) {
        HttpHeaders headers = createHeaders();

        HttpEntity<CommonPaymentRequest> httpEntity = new HttpEntity<>(request, headers);

        TossPaymentResponse toss = restTemplate.postForEntity(
                tossPaymentBaseUrl + tossConfirmEndpoint,
                httpEntity,
                TossPaymentResponse.class).getBody();

        return CommonPaymentResponse.builder()
                .paymentId(toss.getPaymentKey())
                .orderId(toss.getOrderId())
                .totalAmount(toss.getTotalAmount())
                .status(toss.getStatus())
                .approvedAt(toss.getApprovedAt())
                .build();
    }

    @Override
    public void setAuthorization(HttpHeaders headers) {
        String encodedSecretKey = Base64
                .getEncoder()
                .encodeToString((secretKey + COLON_SEPARATOR)
                        .getBytes(StandardCharsets.UTF_8));

        headers.set(AUTHORIZATION, AUTHORIZATION_PREFIX + encodedSecretKey);
    }

    @Override
    public PaymentType getType() {
        return PaymentType.TOSS;
    }
}
