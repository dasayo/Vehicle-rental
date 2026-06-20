package com.alejo.rentadevehiculos.infrastructure.services;

import com.alejo.rentadevehiculos.domain.entities.PaymentMethodEntity;
import com.alejo.rentadevehiculos.domain.entities.RentEntity;
import com.alejo.rentadevehiculos.util.Method;
import com.alejo.rentadevehiculos.util.Status;
import com.alejo.rentadevehiculos.util.encrypt.EncryptionUtil;
import com.alejo.rentadevehiculos.util.exceptions.BadRequestRentalVehiclesException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
@RequiredArgsConstructor
public class PaymentValidationService {

    @Value("${encryption.secret.key}")
    private String secretKeyString;
    private SecretKey secretKey;

    // Convierte el String a SecretKey después de inyectar la clave
    @PostConstruct
    public void init() {
        this.secretKey = EncryptionUtil.getSecretKeyFromBase64(secretKeyString);
    }

    public boolean simulatePayment(RentEntity rent) throws Exception {
        if(rent.getStatus().equals(Status.CLOSED)){
            throw new BadRequestRentalVehiclesException("This rent cannot be used");
        }
        if(rent.getPaymentMethod().getMethod().equals(Method.CASH)){
            return false;
        }
        PaymentMethodEntity paymentMethod = rent.getPaymentMethod();
        String decrypt = EncryptionUtil.decrypt(paymentMethod.getCardNumber(), secretKey);
        return decrypt.startsWith("1");     //revisa si la tarjeta desencriptada empieza con 1,
        // si es asi se simula que el pago fue exitoso, de lo contrario se simula que el pago fue rechazado.
        //teniendo esto en cuenta, el dígito 5 de la tarjeta siempre tiene que ser 1 por lo que el sistema no
        //funciona, si la tarjeta es real y no tiene 1 la va a rechazar.
    }
}
