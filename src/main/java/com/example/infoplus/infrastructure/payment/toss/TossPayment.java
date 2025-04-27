package com.example.infoplus.infrastructure.payment.toss;

import com.example.infoplus.domain.payment.dto.request.PayRequestDto;
import com.example.infoplus.domain.payment.dto.response.PayResponseDto;
import com.example.infoplus.infrastructure.payment.PaymentApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class TossPayment implements PaymentApiClient {

    @Value("${payment.toss.secret-key}")
    private String secretKey;

    @Value("${payment.toss.base-url}")
    private String tossPaymentBaseUrl;

    @Value("${payment.toss.confirm-endpoint}")
    private String tossConfirmEndpoint;

    @Override
    public PayResponseDto.Toss approvePayment(PayRequestDto.Toss payRequestDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String encodedSecretKey = Base64
                .getEncoder()
                .encodeToString((secretKey + ":")
                        .getBytes(StandardCharsets.UTF_8));
        headers.set("Authorization", "Basic " + encodedSecretKey);

        HttpEntity<PayRequestDto.Toss> httpEntity = new HttpEntity<>(payRequestDto, headers);

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.postForEntity(
                tossPaymentBaseUrl + tossConfirmEndpoint,
                httpEntity,
                PayResponseDto.Toss.class).getBody();
    }
}
