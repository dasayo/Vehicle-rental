package com.alejo.rentadevehiculos.infrastructure.abstractServices;

import com.alejo.rentadevehiculos.api.models.request.PaymentMethodRequest;
import com.alejo.rentadevehiculos.api.models.request.PaymentUpdateRequest;
import com.alejo.rentadevehiculos.api.models.response.PaymentMethodResponse;
import com.alejo.rentadevehiculos.api.models.response.SuccesResponse;
import org.springframework.http.ResponseEntity;


import java.util.List;

public interface IPaymentMethodService {



    ResponseEntity<SuccesResponse> addPaymetMethod(PaymentMethodRequest paymentMethodRequest);

    List<PaymentMethodResponse> listPaymentMethod(Long id);

    ResponseEntity<SuccesResponse> editPaymentMethod(PaymentUpdateRequest request);

    ResponseEntity<SuccesResponse> deletePaymentMethod(Long id);
}
