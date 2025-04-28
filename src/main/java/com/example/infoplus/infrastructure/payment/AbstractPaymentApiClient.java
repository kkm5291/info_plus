package com.example.infoplus.infrastructure.payment;

import com.example.infoplus.domain.payment.dto.request.CommonPaymentRequest;
import com.example.infoplus.domain.payment.dto.response.CommonPaymentResponse;
import com.example.infoplus.domain.payment.util.PaymentType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public abstract class AbstractPaymentApiClient implements PaymentApiClient {

    public static final String AUTHORIZATION = "Authorization";

    protected final RestTemplate restTemplate = new RestTemplate();

    public abstract CommonPaymentResponse approvePayment(CommonPaymentRequest request);

    public abstract void setAuthorization(HttpHeaders headers);

    public abstract PaymentType getType();

    public HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        setAuthorization(headers);
        return headers;
    }
}
