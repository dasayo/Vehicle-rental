package com.alejo.rentadevehiculos.infrastructure.services;

import com.alejo.rentadevehiculos.api.models.request.VehicleRequest;
import com.alejo.rentadevehiculos.api.models.response.SuccesResponse;
import com.alejo.rentadevehiculos.api.models.response.VehicleResponse;
import com.alejo.rentadevehiculos.domain.entities.VehicleEntity;
import com.alejo.rentadevehiculos.domain.repositories.VehicleRepository;
import com.alejo.rentadevehiculos.infrastructure.abstractServices.IVehicleService;
import com.alejo.rentadevehiculos.util.exceptions.VehicleNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Data
@AllArgsConstructor
@Builder

public class VehicleService implements IVehicleService {

    private VehicleRepository vehicleRepository;

    @Override
    public VehicleResponse getVehicleByLicensePlate(String licensePlate) {
        return null;
    }

    @Override
    public Set<VehicleResponse> getAllVehicle() {
        List<VehicleEntity> list = vehicleRepository.getAllByIsActiveTrue();
        return list.stream().map(this::toVehicleResponse).collect(Collectors.toSet());
    }

    @Override
    public Set<VehicleResponse> getAllVehicleAvailable() {
        List<VehicleEntity> list = vehicleRepository.getAllByIsAvailableTrueAndIsActiveTrue();
        return list.stream().map(this::toVehicleResponse).collect(Collectors.toSet());
    }

    @Override
    public ResponseEntity<SuccesResponse> createVehicle(VehicleRequest request) {
        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setIsAvailable(true);
        vehicle.setIsActive(true);
        vehicle.setRate(request.getRate());
        vehicle.setLicensePlate(request.getLicensePlate());
        vehicle.setBrand(request.getBrand());
        vehicle.setModel(request.getModel());
        vehicleRepository.save(vehicle);
        return ResponseEntity.ok(new SuccesResponse("Vehicle stored properly"));
    }

    @Override
    public ResponseEntity<SuccesResponse> deletVehicle(String licensePlate) {

        Optional<VehicleEntity> optionalVehicle = vehicleRepository.getVehicle(licensePlate);
        VehicleEntity vehicle = optionalVehicle.
                orElseThrow(()->new VehicleNotFoundException("Error! License Plate is not valid"));
        if (!vehicle.getIsAvailable()){
            return ResponseEntity.badRequest().body(new SuccesResponse("the vehicle cannot be removed"));
        }
        vehicle.setIsActive(false);
        vehicleRepository.save(vehicle);
        return ResponseEntity.ok(new SuccesResponse("vehicle properly disposed of"));
    }

    private VehicleResponse toVehicleResponse(VehicleEntity vehicle){
        return new VehicleResponse(vehicle.getLicensePlate(), vehicle.getBrand(),
                vehicle.getModel(), vehicle.getIsAvailable(), vehicle.getRate());
    }
}
