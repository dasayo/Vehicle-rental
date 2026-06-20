package com.alejo.rentadevehiculos.api.controllers;

import com.alejo.rentadevehiculos.api.models.request.VehicleRequest;
import com.alejo.rentadevehiculos.api.models.response.SuccesResponse;
import com.alejo.rentadevehiculos.api.models.response.VehicleResponse;
import com.alejo.rentadevehiculos.infrastructure.abstractServices.IVehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("vehicle-rental/vehicle")
@RequiredArgsConstructor
public class VehicleController {
    private final IVehicleService vehicleService;

    @PostMapping("/")
    public ResponseEntity<SuccesResponse> createVehicle(@Valid @RequestBody VehicleRequest request){
        vehicleService.createVehicle(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccesResponse("Vehicle stored properly"));
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
    public ResponseEntity<SuccesResponse> deleteVehicle(@RequestParam String licensePlate){
        vehicleService.deletVehicle(licensePlate);
        return ResponseEntity.ok(new SuccesResponse("vehicle properly disposed of"));
    }

}
