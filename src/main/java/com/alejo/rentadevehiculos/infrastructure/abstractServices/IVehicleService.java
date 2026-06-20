package com.alejo.rentadevehiculos.infrastructure.abstractServices;

import com.alejo.rentadevehiculos.api.models.request.VehicleRequest;
import com.alejo.rentadevehiculos.api.models.response.VehicleResponse;

import java.util.Set;

public interface IVehicleService {

    VehicleResponse getVehicleByLicensePlate(String licensePlate);
    Set<VehicleResponse> getAllVehicle();
    Set<VehicleResponse> getAllVehicleAvailable();
    void createVehicle(VehicleRequest request);
    void deletVehicle(String licensePlate);
}

