package com.alejo.rentadevehiculos.infrastructure.services;

import com.alejo.rentadevehiculos.api.models.request.UpdatePasswordRequest;
import com.alejo.rentadevehiculos.api.models.request.UserRequest;
import com.alejo.rentadevehiculos.api.models.response.UserResponse;
import com.alejo.rentadevehiculos.domain.entities.UserEntity;
import com.alejo.rentadevehiculos.domain.repositories.PaymentMethodRepository;
import com.alejo.rentadevehiculos.domain.repositories.UserRepository;
import com.alejo.rentadevehiculos.infrastructure.abstractServices.IUserService;
import com.alejo.rentadevehiculos.util.exceptions.UserNotFoundExeption;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements IUserService {

    private final PaymentMethodRepository paymentMethodRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final PaymentMethodService paymentMethodService;

    @Override
    public void userCreate(UserRequest userRequest) {
        UserEntity user = UserEntity.builder()
                 .username(userRequest.getUsername())
                 .firstName(userRequest.getFirstName())
                 .lastName(userRequest.getLastName())
                 .isActive(true)
                 .registrationDate(LocalDateTime.now())
                 .password(encryptPassword(userRequest.getPassword()))
                 .paymentMethods(new HashSet<>())
                 .build();

        log.info(String.valueOf(user));

        userRepository.save(user);

        log.info(String.valueOf(user));
    }

    @Override
    public void updatePasswordUser(UpdatePasswordRequest updatePasswordRequest) {
        UserEntity userEntity = userRepository.findUserById(updatePasswordRequest.getId())
                .orElseThrow(() -> new UserNotFoundExeption("Error el usuario no existe"));

        userEntity.assignEncryptedPassword(encryptPassword(updatePasswordRequest.getNewPassword()));
        userRepository.save(userEntity);
    }

    @Override
    public List<UserResponse> listUsers() {
        List<Object[]> reg = userRepository.listUser();
        List<UserResponse> users =new ArrayList<>();
        reg.forEach(u -> users.add(toUserResponse(u)));
        return users;
    }

    @Override
    public UserResponse findUserByid(Long id) {
        return userRepository.findUserById(id)
                .map(this::toUserResponse)
                .orElseThrow(() -> new UserNotFoundExeption("Error usuario no existe"));
    }

    @Override
    public void deleteUserBy(Long id) {
        UserEntity userEntity = userRepository.findUserById(id)
                .orElseThrow(() -> new UserNotFoundExeption("Error el usuario no existe"));
        userEntity.setIsActive(false);
        userRepository.save(userEntity);
    }

    private String encryptPassword(String password){
        return bCryptPasswordEncoder.encode(password);

    }

    public UserResponse toUserResponse(Object[] object){
        UserResponse userResponse = new UserResponse();
        userResponse.setUsername((String) object[0]);
        userResponse.setFirstName((String) object[1]);
        userResponse.setLastName((String) object[2]);
        userResponse.setRegistrationDate((LocalDateTime) object[3]);
        return userResponse;
    }

    public UserResponse toUserResponse(UserEntity object) {
        UserResponse userResponse = new UserResponse();
        userResponse.setUsername(object.getUsername());
        userResponse.setFirstName(object.getFirstName());
        userResponse.setLastName(object.getLastName());
        userResponse.setRegistrationDate(object.getRegistrationDate());

        userResponse.setMethodResponseList(paymentMethodRepository.findAllByIdUser(object)
                .stream()
                .map(paymentMethodService::toPaymentMethodResponse)
                .collect(Collectors
                        .toSet()));

        return userResponse;
    }

}
