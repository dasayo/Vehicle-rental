package com.alejo.rentadevehiculos.config;



import com.alejo.rentadevehiculos.util.encrypt.EncryptionUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
public class EncryptionConfig {

//    @Bean
    public SecretKey secretKey() throws Exception {
        return EncryptionUtil.generateKey(); // Genera y devuelve la clave
    }

}


