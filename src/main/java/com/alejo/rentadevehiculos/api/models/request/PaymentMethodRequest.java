package com.alejo.rentadevehiculos.api.models.request;

import com.alejo.rentadevehiculos.util.Method;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PaymentMethodRequest {

    @NotNull
    @Positive
    private Long id_user;

    @Size(min = 13, max = 19, message = "Card number must be between 13 and 19 digits")
    private String cardNumber;

    @NotNull(message = "The Method cannot be Null")
    private Method method;
}
