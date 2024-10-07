package com.alejo.rentadevehiculos.api.models.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentRequest {

    @NotNull(message = "Vehicle ID cannot be null.")
    private String vehicleId;

    @NotNull(message = "User ID cannot be null.")
    private Long userId;

    @NotNull(message = "Payment method ID cannot be null.")
    private Long paymentMethodId;




}
