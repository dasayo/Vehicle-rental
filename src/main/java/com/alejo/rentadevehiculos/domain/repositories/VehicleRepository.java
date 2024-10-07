package com.alejo.rentadevehiculos.domain.repositories;

import com.alejo.rentadevehiculos.domain.entities.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface VehicleRepository extends JpaRepository<VehicleEntity,String> {

    List<VehicleEntity> getAllByIsAvailableTrueAndIsActiveTrue();
    List<VehicleEntity> getAllByIsActiveTrue();

    @Query("select v from vehicle v where v.isActive = true and  v.licensePlate = :s")
    Optional<VehicleEntity> getVehicle(String s);
}
