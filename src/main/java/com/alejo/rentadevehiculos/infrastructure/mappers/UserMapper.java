package com.alejo.rentadevehiculos.infrastructure.mappers;

import com.alejo.rentadevehiculos.api.models.response.UserResponse;
import com.alejo.rentadevehiculos.domain.entities.UserEntity;
import com.alejo.rentadevehiculos.domain.repositories.PaymentMethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentMethodMapper paymentMethodMapper;

    public UserResponse toResponse(UserEntity entity) {
        return  UserResponse.builder()
                .username(entity.getUsername())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .registrationDate(entity.getRegistrationDate())
                .methodResponseList(paymentMethodRepository.findAllByIdUser(entity)
                        .stream()
                        .map(paymentMethodMapper::toResponse)
                        .collect(Collectors.toSet()))
                .build();
    }

    public UserResponse toResponse(Object[] row) {
        return UserResponse.builder()
                .username((String) row[0])
                .firstName((String) row[1])
                .lastName((String) row[2])
                .registrationDate((LocalDateTime) row[3])
                .build();
    }
}
