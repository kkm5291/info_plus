package com.example.infoplus.infrastructure.payment;

import com.example.infoplus.domain.payment.dto.request.CommonPaymentRequest;
import com.example.infoplus.domain.payment.dto.request.PaymentRequest;
import com.example.infoplus.domain.payment.dto.response.PaymentResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public abstract class AbstractPaymentApiClient implements PaymentApiClient {

    public static final String AUTHORIZATION = "Authorization";

    protected final RestTemplate restTemplate = new RestTemplate();

    public abstract PaymentResponse approvePayment(CommonPaymentRequest request);

    public abstract void setAuthorization(HttpHeaders headers);

    public HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        setAuthorization(headers);
        return headers;
    }
}
