package com.alejo.rentadevehiculos.api.controllers.error_handler;

import com.alejo.rentadevehiculos.api.models.response.ErrorResponse;
import com.alejo.rentadevehiculos.util.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundExeption.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFound(UserNotFoundExeption exeption) {
        log.error(exeption.getMessage());
        return ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message("The user does not exist")
                .timestamp(new Date())
                .build();
    }

    @ExceptionHandler(VehicleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleVehicleNotFound(VehicleNotFoundException exeption) {
        log.error(exeption.getMessage());
        return ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message("The vehicle does not exist")
                .timestamp(new Date())
                .build();
    }

    @ExceptionHandler(PaymentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public  ErrorResponse paymentMethodNotFound(PaymentNotFoundException exeption) {
        log.error(exeption.getMessage());
        return ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message("The payment method does not exist")
                .timestamp(new Date())
                .build();
    }

    @ExceptionHandler(RentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse rentNotFound(RentNotFoundException exeption) {
        log.error(exeption.getMessage());
        return ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message("Rent not found.")
                .timestamp(new Date())
                .build();
    }

    @ExceptionHandler(BadRequestRentalVehiclesException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse badRequest(BadRequestRentalVehiclesException exeption) {
        log.error(exeption.getMessage());
        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("The data entered are not valid to create a Rent.")
                .timestamp(new Date())
                .build();
    }
}
