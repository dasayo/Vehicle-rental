package com.alejo.rentadevehiculos.infrastructure.services;

import com.alejo.rentadevehiculos.api.models.request.PaymentMethodRequest;
import com.alejo.rentadevehiculos.api.models.request.PaymentUpdateRequest;
import com.alejo.rentadevehiculos.api.models.response.PaymentMethodResponse;
import com.alejo.rentadevehiculos.api.models.response.SuccesResponse;
import com.alejo.rentadevehiculos.domain.entities.PaymentMethodEntity;
import com.alejo.rentadevehiculos.domain.entities.UserEntity;
import com.alejo.rentadevehiculos.domain.repositories.PaymentMethodRepository;
import com.alejo.rentadevehiculos.domain.repositories.UserRepository;
import com.alejo.rentadevehiculos.infrastructure.abstractServices.IPaymentMethodService;
import com.alejo.rentadevehiculos.util.Method;
import com.alejo.rentadevehiculos.util.encrypt.EncryptionUtil;
import com.alejo.rentadevehiculos.util.exceptions.PaymentNotFoundException;
import com.alejo.rentadevehiculos.util.exceptions.UserNotFoundExeption;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.List;
import java.util.Optional;

@Service
@Data

public class PaymentMethodService implements IPaymentMethodService {

    private final UserRepository userRepository;
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Value("${encryption.secret.key}")
    private String secretKeyString;

    private SecretKey secretKey;

    // Convierte el String a SecretKey después de inyectar la clave
    @PostConstruct
    public void init() {
        this.secretKey = EncryptionUtil.getSecretKeyFromBase64(secretKeyString);
    }


    @Override
    public ResponseEntity<SuccesResponse> addPaymetMethod(PaymentMethodRequest paymentMethodRequest) {

        UserEntity user = userRepository.findUserById(paymentMethodRequest.
                getId_user()).
                orElseThrow(()-> new UserNotFoundExeption("It is necessary that the user exists"));

        PaymentMethodEntity paymentMethod = new PaymentMethodEntity();
        paymentMethod.setIsActive(true);

        if(!paymentMethodRequest.getMethod().name().equals(Method.CASH.name())){
            try {
                paymentMethod.setCardNumber(encryptCardNumber(paymentMethodRequest.getCardNumber()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            paymentMethod.setMethod(paymentMethodRequest.getMethod());
            paymentMethod.setLastDigits(paymentMethodRequest.
                    getCardNumber().
                    substring(paymentMethodRequest.getCardNumber().length() - 4));

            paymentMethod.setUser(user);
            paymentMethodRepository.save(paymentMethod);

        }else {
            paymentMethod.setMethod(Method.CASH);
            paymentMethod.setUser(user);
            paymentMethodRepository.save(paymentMethod);
        }

        return ResponseEntity.ok(new SuccesResponse("method added correctly"));
    }

    @Override
    public List<PaymentMethodResponse> listPaymentMethod(Long id) {
        return List.of();
    }

    @Override
    public ResponseEntity<SuccesResponse> editPaymentMethod(PaymentUpdateRequest request) {
        PaymentMethodEntity paymentMethod= requesToPaymentEntiy(request);
        paymentMethodRepository.save(paymentMethod);
        return ResponseEntity.ok(new SuccesResponse("Method updated correctly"));
    }

    @Override
    public ResponseEntity<SuccesResponse> deletePaymentMethod(Long id) {

        PaymentMethodEntity paymentMethodEntity = paymentMethodRepository.
                findPaymentByPaymentId(id).orElseThrow(()->new PaymentNotFoundException("Error id is not valid"));
        paymentMethodEntity.setIsActive(false);
        paymentMethodRepository.save(paymentMethodEntity);
        return ResponseEntity.ok(new SuccesResponse("payment method correctly removed"));
    }

    private String encryptCardNumber(String cardNumber) throws Exception{
        return EncryptionUtil.encrypt(cardNumber, secretKey);
    }

    public PaymentMethodResponse toPaymentMethodResponse(PaymentMethodEntity paymentMethod){
        return new PaymentMethodResponse(paymentMethod.getId(),paymentMethod.getMethod(),
                paymentMethod.getCardNumber(), paymentMethod.getLastDigits());
    }

    private PaymentMethodEntity requesToPaymentEntiy(PaymentUpdateRequest request){

        Optional<PaymentMethodEntity> paymentMethodEntityOptional=
                paymentMethodRepository.findPaymentByPaymentId(request.getId());
        PaymentMethodEntity paymentMethod= paymentMethodEntityOptional.
                orElseThrow(()->new PaymentNotFoundException("Error id is not valid"));
        try {
            paymentMethod.setCardNumber(encryptCardNumber(request.getNewCardNumber()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        paymentMethod.setLastDigits(request.getNewCardNumber()
                .substring(request.getNewCardNumber().length()-4));
        return paymentMethod;
    }

}
