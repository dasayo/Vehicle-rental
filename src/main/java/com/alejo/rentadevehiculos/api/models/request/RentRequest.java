package com.alejo.rentadevehiculos.api.models.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentRequest {

    @NotBlank(message = "Vehicle ID cannot be blank.")
    private String vehicleId;

    @NotNull(message = "User ID cannot be null.")
    @Positive
    private Long userId;

    @NotNull(message = "Payment method ID cannot be null.")
    @Positive
    private Long paymentMethodId;
}
