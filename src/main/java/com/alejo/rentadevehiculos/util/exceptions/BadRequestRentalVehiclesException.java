package com.alejo.rentadevehiculos.util.exceptions;

public class BadRequestRentalVehiclesException extends RuntimeException{
    public BadRequestRentalVehiclesException(String message) {
        super(message);
    }
}
