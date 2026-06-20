package com.alejo.rentadevehiculos.infrastructure.abstractServices;

import com.alejo.rentadevehiculos.api.models.request.RentRequest;
import com.alejo.rentadevehiculos.api.models.response.RentResponse;

import java.util.Set;

public interface IRentService {

    void createRent(RentRequest request);
    Set<RentResponse> getAllRents();
    RentResponse getRentById(Long id);
    RentResponse updateStatus(Long id) throws Exception;
    Set<RentResponse> getRentsByUserId(Long id);

}
