package com.alejo.rentadevehiculos.api.models.response;

import com.alejo.rentadevehiculos.domain.entities.PaymentMethodEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Component
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserResponse {
    private String username;
    private String firstName;
    private String lastName;
    private LocalDateTime registrationDate;
    private Set<PaymentMethodResponse> methodResponseList;
}
