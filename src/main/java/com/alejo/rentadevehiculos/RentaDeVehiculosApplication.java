package com.alejo.rentadevehiculos;

import com.alejo.rentadevehiculos.util.encrypt.EncryptionUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.crypto.SecretKey;
import java.util.Base64;

@SpringBootApplication
public class RentaDeVehiculosApplication  {

    public static void main(String[] args) {
        SpringApplication.run(RentaDeVehiculosApplication.class, args);
    }


}
