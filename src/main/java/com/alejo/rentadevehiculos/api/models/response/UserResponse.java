package com.alejo.rentadevehiculos.api.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



import java.time.LocalDateTime;

import java.util.Set;
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
