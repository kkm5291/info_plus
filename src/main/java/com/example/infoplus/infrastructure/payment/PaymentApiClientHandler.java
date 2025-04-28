package com.example.infoplus.infrastructure.payment;

import com.example.infoplus.domain.payment.util.PaymentType;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PaymentApiClientHandler {

    private final Map<PaymentType, PaymentApiClient> paymentApiClientMap = new EnumMap<>(PaymentType.class);
    private final List<PaymentApiClient> clients;

    @PostConstruct
    public void init() {
        for (PaymentApiClient client : clients) {
            paymentApiClientMap.put(client.getType(), client);
        }
    }


    public PaymentApiClient getClient(PaymentType type) {
        PaymentApiClient client = paymentApiClientMap.get(type);
        if (client == null) {
            throw new IllegalArgumentException("지원하지 않는 결제 타입입니다: " + type);
        }
        return client;
    }

}
