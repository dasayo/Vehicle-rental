package com.alejo.rentadevehiculos.infrastructure.abstractServices;

import com.alejo.rentadevehiculos.api.models.request.PaymentMethodRequest;
import com.alejo.rentadevehiculos.api.models.request.PaymentUpdateRequest;
import com.alejo.rentadevehiculos.api.models.response.PaymentMethodResponse;


import java.util.List;

public interface IPaymentMethodService {



    void addPaymetMethod(PaymentMethodRequest paymentMethodRequest);

    List<PaymentMethodResponse> listPaymentMethod(Long id);

    void editPaymentMethod(PaymentUpdateRequest request);

    void deletePaymentMethod(Long id);
}
