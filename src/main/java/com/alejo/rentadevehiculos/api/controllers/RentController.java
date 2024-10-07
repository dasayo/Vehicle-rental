package com.alejo.rentadevehiculos.api.controllers;

import com.alejo.rentadevehiculos.api.models.request.RentRequest;
import com.alejo.rentadevehiculos.api.models.response.SuccesResponse;
import com.alejo.rentadevehiculos.domain.entities.RentEntity;
import com.alejo.rentadevehiculos.infrastructure.services.RentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/vehicle-rental/rent")
@AllArgsConstructor
@Data
@Builder
public class RentController {


    private final RentService rentService;



    @PostMapping("/")
    public ResponseEntity<SuccesResponse> createRent(@Valid @RequestBody RentRequest request){
       return rentService.createRent(request);
    }

    @GetMapping("/listAll")
    public Set<RentEntity> getAll(){
       return rentService.getAllRents();
    }

    @GetMapping("/")
    public RentEntity getById(@RequestParam Long id){
        return rentService.getRentById(id);
    }
    @GetMapping("/user")
    public Set<RentEntity> getRentByUserId(@RequestParam Long id){
        return rentService.getRentsByUserId(id);
    }

    @PutMapping("/close")
    public ResponseEntity<SuccesResponse> closeRent(@RequestParam Long id) throws Exception {
        return rentService.updateStatus(id);
    }

}
