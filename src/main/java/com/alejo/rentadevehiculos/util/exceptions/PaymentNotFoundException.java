package com.alejo.rentadevehiculos.util.exceptions;

public class PaymentNotFoundException extends RuntimeException{
    public PaymentNotFoundException(String msg){
        super(msg);
    }
}
