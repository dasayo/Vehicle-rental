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
import com.alejo.rentadevehiculos.infrastructure.mappers.PaymentMethodMapper;
import com.alejo.rentadevehiculos.util.Method;
import com.alejo.rentadevehiculos.util.exceptions.PaymentNotFoundException;
import com.alejo.rentadevehiculos.util.exceptions.UserNotFoundExeption;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentMethodService implements IPaymentMethodService {

    private final UserRepository userRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final CardEncryptionService cardEncryptionService;
    private final PaymentMethodMapper paymentMethodMapper;

    @Override
    public void addPaymetMethod(PaymentMethodRequest request) {
        UserEntity user = userRepository.findUserById(request.getId_user())
                .orElseThrow(() -> new UserNotFoundExeption("It is necessary that the user exists"));

        PaymentMethodEntity paymentMethod = paymentMethodMapper.toEntity(request, user);
        if (!request.getMethod().name().equals(Method.CASH.name())) {
            paymentMethod.assignEncryptedCardNumber(
                    this.cardEncryptionService.encrypt(paymentMethod.getCardNumber())
            );
        }
        paymentMethodRepository.save(paymentMethod);
    }
    @Override
    @Transactional(readOnly = true)
    public List<PaymentMethodResponse> listPaymentMethod(Long id) {
        return List.of();
    }

    @Override
    public void editPaymentMethod(PaymentUpdateRequest request) {
        PaymentMethodEntity paymentMethod = paymentMethodRepository
                .findPaymentByPaymentId(request.getId())
                .orElseThrow(() -> new PaymentNotFoundException("Error id is not valid"));
        paymentMethod.assignEncryptedCardNumber(
                this.cardEncryptionService.encrypt(request.getNewCardNumber())
        );
        paymentMethod.setLastDigits(request.getNewCardNumber().substring(request.getNewCardNumber().length() - 4));
        paymentMethodRepository.save(paymentMethod);
    }

    @Override
    public void deletePaymentMethod(Long id) {
        PaymentMethodEntity paymentMethod = paymentMethodRepository
                .findPaymentByPaymentId(id)
                .orElseThrow(() -> new PaymentNotFoundException("Error id is not valid"));
        paymentMethod.setIsActive(false);
        paymentMethodRepository.save(paymentMethod);
    }
}
