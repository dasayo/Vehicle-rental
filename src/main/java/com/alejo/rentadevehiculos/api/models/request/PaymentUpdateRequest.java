package com.alejo.rentadevehiculos.api.models.request;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PaymentUpdateRequest {
    @NotNull
    private Long id;
    @NotNull
    private String newCardNumber;
}
