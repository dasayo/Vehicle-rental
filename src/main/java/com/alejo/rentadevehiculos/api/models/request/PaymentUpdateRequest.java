package com.alejo.rentadevehiculos.api.models.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PaymentUpdateRequest {
    @NotNull
    @Positive
    private Long id;
    @NotBlank
    @Size(min = 13, max = 19, message = "Card number must be between 13 and 19 digits")
    private String newCardNumber;
}
