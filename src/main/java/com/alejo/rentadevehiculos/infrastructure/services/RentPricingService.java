package com.alejo.rentadevehiculos.infrastructure.services;

import com.alejo.rentadevehiculos.util.exceptions.BadRequestRentalVehiclesException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class RentPricingService {

    public BigDecimal calculatePrice(LocalDateTime start, LocalDateTime end, BigDecimal rate) {
        if (!start.isBefore(end)) {
            throw new BadRequestRentalVehiclesException("Verify the dates");
        }
        long hours = Duration.between(start, end).toHours();
        if (hours == 0) {
            hours = 1L;
        }
        return rate.multiply(BigDecimal.valueOf(hours));
    }
}
