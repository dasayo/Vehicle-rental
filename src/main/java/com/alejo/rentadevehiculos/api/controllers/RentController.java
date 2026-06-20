package com.alejo.rentadevehiculos.api.controllers;

import com.alejo.rentadevehiculos.api.models.request.RentRequest;
import com.alejo.rentadevehiculos.api.models.response.RentResponse;
import com.alejo.rentadevehiculos.api.models.response.SuccesResponse;
import com.alejo.rentadevehiculos.infrastructure.abstractServices.IRentService;
import com.alejo.rentadevehiculos.util.Status;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/vehicle-rental/rent")
@RequiredArgsConstructor
public class RentController {

    private final IRentService rentService;



    @PostMapping("/")
    public ResponseEntity<SuccesResponse> createRent(@Valid @RequestBody RentRequest request){
        rentService.createRent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccesResponse("Rent created in the correct manner"));
    }

    @GetMapping("/listAll")
    public Set<RentResponse> getAll(){
       return rentService.getAllRents();
    }

    @GetMapping("/")
    public RentResponse getById(@RequestParam Long id){
        return rentService.getRentById(id);
    }

    @GetMapping("/user")
    public Set<RentResponse> getRentByUserId(@RequestParam Long id){
        return rentService.getRentsByUserId(id);
    }

    @PutMapping("/close")
    public ResponseEntity<SuccesResponse> closeRent(@RequestParam Long id) throws Exception {
        RentResponse rent = rentService.updateStatus(id);
        if (rent.getStatus() == Status.UNDER_REVIEW) {
            return ResponseEntity.accepted().body(new SuccesResponse("The rent is under review (UNDER_REVIEW). No further actions can be performed at this time."));
        }
        return ResponseEntity.ok(new SuccesResponse("Rent closed in the correct manner"));
    }

}
