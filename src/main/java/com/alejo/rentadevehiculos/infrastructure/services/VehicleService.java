package com.alejo.rentadevehiculos.infrastructure.services;

import com.alejo.rentadevehiculos.api.models.request.VehicleRequest;
import com.alejo.rentadevehiculos.api.models.response.VehicleResponse;
import com.alejo.rentadevehiculos.domain.entities.VehicleEntity;
import com.alejo.rentadevehiculos.domain.repositories.VehicleRepository;
import com.alejo.rentadevehiculos.infrastructure.abstractServices.IVehicleService;
import com.alejo.rentadevehiculos.infrastructure.mappers.VehicleMapper;
import com.alejo.rentadevehiculos.util.exceptions.BadRequestRentalVehiclesException;
import com.alejo.rentadevehiculos.util.exceptions.VehicleNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class VehicleService implements IVehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;

    @Override
    @Transactional(readOnly = true)
    public VehicleResponse getVehicleByLicensePlate(String licensePlate) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<VehicleResponse> getAllVehicle() {
        List<VehicleEntity> list = vehicleRepository.getAllByIsActiveTrue();
        return list.stream().map(vehicleMapper::toResponse).collect(Collectors.toSet());
    }

    @Override
    @Transactional(readOnly = true)
    public Set<VehicleResponse> getAllVehicleAvailable() {
        List<VehicleEntity> list = vehicleRepository.getAllByIsAvailableTrueAndIsActiveTrue();
        return list.stream().map(vehicleMapper::toResponse).collect(Collectors.toSet());
    }

    @Override
    public void createVehicle(VehicleRequest request) {
        VehicleEntity vehicle = VehicleEntity.builder()
                .licensePlate(request.getLicensePlate())
                .brand(request.getBrand())
                .model(request.getModel())
                .rate(request.getRate())
                .isAvailable(true)
                .isActive(true)
                .build();
        vehicleRepository.save(vehicle);
    }

    @Override
    public void deletVehicle(String licensePlate) {
        VehicleEntity vehicle = vehicleRepository.getVehicle(licensePlate)
                .orElseThrow(() -> new VehicleNotFoundException("Error! License Plate is not valid"));
        if (!vehicle.getIsAvailable()) {
            throw new BadRequestRentalVehiclesException("the vehicle cannot be removed");
        }
        vehicle.setIsActive(false);
        vehicleRepository.save(vehicle);
    }

}
