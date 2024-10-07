package com.alejo.rentadevehiculos.infrastructure.abstractServices;

import com.alejo.rentadevehiculos.api.models.request.RentRequest;
import com.alejo.rentadevehiculos.api.models.response.SuccesResponse;
import com.alejo.rentadevehiculos.domain.entities.RentEntity;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface IRentService {

    ResponseEntity<SuccesResponse> createRent(RentRequest request);
    Set<RentEntity> getAllRents();
    RentEntity getRentById(Long id);
    ResponseEntity<SuccesResponse> updateStatus(Long id) throws Exception;
    Set<RentEntity> getRentsByUserId(Long id);

}
