package com.alejo.rentadevehiculos.api.models.request;

import com.alejo.rentadevehiculos.util.Method;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PaymentMethodRequest {

    private Long id_user;

    private String cardNumber;

    @NotNull(message = "The Method cannot be Null")
    private Method method;


}
