package com.alejo.rentadevehiculos.util.exceptions;

public class RentNotFoundException extends RuntimeException{
    public RentNotFoundException(String message) {
        super(message);
    }
}
