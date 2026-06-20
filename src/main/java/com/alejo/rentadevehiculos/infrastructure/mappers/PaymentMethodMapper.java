package com.alejo.rentadevehiculos.infrastructure.mappers;

import com.alejo.rentadevehiculos.api.models.request.PaymentMethodRequest;
import com.alejo.rentadevehiculos.api.models.response.PaymentMethodResponse;
import com.alejo.rentadevehiculos.domain.entities.PaymentMethodEntity;
import com.alejo.rentadevehiculos.domain.entities.UserEntity;
import com.alejo.rentadevehiculos.util.Method;
import org.springframework.stereotype.Component;

@Component

public class PaymentMethodMapper {

    public PaymentMethodResponse toResponse(PaymentMethodEntity paymentMethod) {
        return new PaymentMethodResponse(
                paymentMethod.getId(),
                paymentMethod.getMethod(),
                paymentMethod.getCardNumber(),
                paymentMethod.getLastDigits()
        );
    }

    public PaymentMethodEntity toEntity(PaymentMethodRequest request, UserEntity user) {
        if (request.getMethod().name().equals("CASH")) {
            return PaymentMethodEntity.builder()
                    .isActive(true)
                    .method(Method.CASH)
                    .user(user)
                    .build();
        }
        String cardNumber = request.getCardNumber();
        return PaymentMethodEntity.builder()
                .isActive(true)
                .method(request.getMethod())
                .lastDigits(cardNumber.substring(cardNumber.length() - 4))
                .user(user)
                .build();
    }


}
