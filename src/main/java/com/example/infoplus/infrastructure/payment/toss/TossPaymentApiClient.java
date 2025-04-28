package com.example.infoplus.infrastructure.payment.toss;

import com.example.infoplus.domain.payment.dto.request.CommonPaymentRequest;
import com.example.infoplus.domain.payment.dto.request.PaymentRequest;
import com.example.infoplus.domain.payment.dto.request.TossPaymentRequest;
import com.example.infoplus.domain.payment.dto.response.PaymentResponse;
import com.example.infoplus.domain.payment.dto.response.TossPaymentResponse;
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
    public PaymentResponse approvePayment(CommonPaymentRequest request) {
        HttpHeaders headers = createHeaders();
        HttpEntity<PaymentRequest> httpEntity = new HttpEntity<>(paymentRequest, headers);

        return restTemplate.postForEntity(
                tossPaymentBaseUrl + tossConfirmEndpoint,
                httpEntity,
                TossPaymentResponse.class).getBody();
    }

    @Override
    public void setAuthorization(HttpHeaders headers) {
        String encodedSecretKey = Base64
                .getEncoder()
                .encodeToString((secretKey + COLON_SEPARATOR)
                        .getBytes(StandardCharsets.UTF_8));

        headers.set(AUTHORIZATION, AUTHORIZATION_PREFIX + encodedSecretKey);
    }
}
