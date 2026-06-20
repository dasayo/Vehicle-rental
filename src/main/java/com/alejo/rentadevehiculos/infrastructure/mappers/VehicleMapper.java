package com.alejo.rentadevehiculos.infrastructure.mappers;

import com.alejo.rentadevehiculos.api.models.response.VehicleResponse;
import com.alejo.rentadevehiculos.domain.entities.VehicleEntity;
import org.springframework.stereotype.Component;

@Component
public class VehicleMapper {

    public VehicleResponse toResponse(VehicleEntity vehicle) {
        return new VehicleResponse(
                vehicle.getLicensePlate(),
                vehicle.getBrand(),
                vehicle.getModel(),
                vehicle.getIsAvailable(),
                vehicle.getRate()
        );
    }
}
