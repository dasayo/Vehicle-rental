package com.alejo.rentadevehiculos.api.controllers;

import com.alejo.rentadevehiculos.api.models.request.VehicleRequest;
import com.alejo.rentadevehiculos.api.models.response.SuccesResponse;
import com.alejo.rentadevehiculos.api.models.response.VehicleResponse;
import com.alejo.rentadevehiculos.infrastructure.abstractServices.IVehicleService;
import com.alejo.rentadevehiculos.infrastructure.services.VehicleService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("vehicle-rental/vehicle")
@AllArgsConstructor
@Data
@Builder
public class VehicleController {
    private IVehicleService vehicleService;

    @PostMapping("/")
    public ResponseEntity<SuccesResponse> createVehicle(@Valid @RequestBody VehicleRequest request){
        return vehicleService.createVehicle(request);
    }

    @GetMapping("/all")
    private Set<VehicleResponse> getAll(){
        return vehicleService.getAllVehicle();
    }

    @GetMapping("/onlyAvailable")
    private Set<VehicleResponse> getOnlyAvailable(){
        return vehicleService.getAllVehicleAvailable();
    }
    @DeleteMapping("/delete")
    private ResponseEntity<SuccesResponse> deleteVehicle(@RequestParam String licensePlate){
        return vehicleService.deletVehicle(licensePlate);
    }

}
