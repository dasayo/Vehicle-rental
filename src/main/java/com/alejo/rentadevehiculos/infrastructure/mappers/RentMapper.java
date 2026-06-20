package com.alejo.rentadevehiculos.infrastructure.mappers;

import com.alejo.rentadevehiculos.api.models.response.RentResponse;
import com.alejo.rentadevehiculos.domain.entities.RentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RentMapper {

    private final PaymentMethodMapper paymentMethodMapper;

    public RentResponse toResponse(RentEntity rentEntity) {
        return RentResponse.builder()
                .startDate(rentEntity.getStartDate())
                .endDate(rentEntity.getEndDate())
                .value(rentEntity.getValue())
                .status(rentEntity.getStatus())
                .paymentMethod(paymentMethodMapper.toResponse(rentEntity.getPaymentMethod()))
                .build();
    }
}
