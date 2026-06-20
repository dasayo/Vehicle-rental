package com.alejo.rentadevehiculos.infrastructure.abstractServices;

import com.alejo.rentadevehiculos.api.models.request.RentRequest;
import com.alejo.rentadevehiculos.domain.entities.RentEntity;

import java.util.Set;

public interface IRentService {

    void createRent(RentRequest request);
    Set<RentEntity> getAllRents();
    RentEntity getRentById(Long id);
    RentEntity updateStatus(Long id) throws Exception;
    Set<RentEntity> getRentsByUserId(Long id);

}
