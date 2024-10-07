package com.alejo.rentadevehiculos.api.models.response;

import com.alejo.rentadevehiculos.util.Method;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentMethodResponse {

    private Long id;
    private Method method;
    private String cardNumber;
    private String lastDigits;

}
