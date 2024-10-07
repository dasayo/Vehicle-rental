package com.alejo.rentadevehiculos.infrastructure.abstractServices;

import com.alejo.rentadevehiculos.api.models.request.VehicleRequest;
import com.alejo.rentadevehiculos.api.models.response.SuccesResponse;
import com.alejo.rentadevehiculos.api.models.response.VehicleResponse;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface IVehicleService {

    VehicleResponse getVehicleByLicensePlate(String licensePlate);
    Set<VehicleResponse> getAllVehicle();
    Set<VehicleResponse> getAllVehicleAvailable();
    ResponseEntity<SuccesResponse> createVehicle(VehicleRequest request);
    ResponseEntity<SuccesResponse> deletVehicle(String licensePlate);
}

