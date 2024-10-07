package com.alejo.rentadevehiculos.api.models.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class VehicleRequest {

    @NotNull
    @Size(min = 5, max = 20,message = "The size have to a length between 5 and 20 characters")
    private String licensePlate;
    @NotNull
    @Size(min = 1, max = 20,message = "The size have to a length between 5 and 20 characters")
    private String brand;

    @NotNull
    private BigDecimal rate;

    @NotNull
    @Size(min = 5, max = 20,message = "The size have to a length between 5 and 20 characters")
    private String model;

}
