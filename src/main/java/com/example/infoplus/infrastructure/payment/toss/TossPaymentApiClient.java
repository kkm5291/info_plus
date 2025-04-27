package com.example.infoplus.infrastructure.payment.toss;

import com.example.infoplus.domain.payment.dto.request.PaymentRequest;
import com.example.infoplus.domain.payment.dto.response.PaymentResponse;
import com.example.infoplus.infrastructure.payment.PaymentApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class TossPaymentApiClient implements PaymentApiClient {

    @Value("${payment.toss.secret-key}")
    private String secretKey;

    @Value("${payment.toss.base-url}")
    private String tossPaymentBaseUrl;

    @Value("${payment.toss.confirm-endpoint}")
    private String tossConfirmEndpoint;

    @Override
    public PaymentResponse.Toss approvePayment(PaymentRequest.Toss paymentRequestDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String encodedSecretKey = Base64
                .getEncoder()
                .encodeToString((secretKey + ":")
                        .getBytes(StandardCharsets.UTF_8));
        headers.set("Authorization", "Basic " + encodedSecretKey);

        HttpEntity<PaymentRequest.Toss> httpEntity = new HttpEntity<>(paymentRequestDto, headers);

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.postForEntity(
                tossPaymentBaseUrl + tossConfirmEndpoint,
                httpEntity,
                PaymentResponse.Toss.class).getBody();
    }
}
