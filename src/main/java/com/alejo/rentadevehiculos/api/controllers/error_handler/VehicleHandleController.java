package com.alejo.rentadevehiculos.api.controllers.error_handler;


import com.alejo.rentadevehiculos.util.exceptions.UserNotFoundExeption;
import com.alejo.rentadevehiculos.util.exceptions.VehicleNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class VehicleHandleController {

    @ExceptionHandler(VehicleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<?,?> idNotFound(VehicleNotFoundException exeption){
        Map<String,String> map= new HashMap<>();
        map.put("msg","The vehicle does not exist");
        map.put("error", exeption.getMessage());
        map.put("date", new Date().toString());
        map.put("status", String.valueOf(HttpStatus.NOT_FOUND.value()));
        log.error(map.get("error"));
        return map;
    }
}
