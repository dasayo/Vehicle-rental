package com.alejo.rentadevehiculos.infrastructure.services;

import com.alejo.rentadevehiculos.util.encrypt.EncryptionUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
@RequiredArgsConstructor
public class CardEncryptionService {

    @Value("${encryption.secret.key}")
    private String secretKeyString;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = EncryptionUtil.getSecretKeyFromBase64(secretKeyString);
    }

    public String encrypt(String cardNumber) {
        try {
            return EncryptionUtil.encrypt(cardNumber, secretKey);
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting card number", e);
        }
    }
}
