package com.alejo.rentadevehiculos.api.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class VehicleResponse {

    private String licensePlate;
    private String brand;
    private String model;
    private Boolean isAvailable;
    private BigDecimal rate;
}
