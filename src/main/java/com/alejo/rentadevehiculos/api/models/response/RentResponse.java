package com.alejo.rentadevehiculos.api.models.response;

import com.alejo.rentadevehiculos.util.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RentResponse {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BigDecimal value;
    private Status status;
    private PaymentMethodResponse paymentMethod;
}
