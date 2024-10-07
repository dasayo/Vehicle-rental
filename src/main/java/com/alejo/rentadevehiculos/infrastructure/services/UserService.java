package com.alejo.rentadevehiculos.infrastructure.services;

import com.alejo.rentadevehiculos.api.models.request.UpdatePasswordRequest;
import com.alejo.rentadevehiculos.api.models.request.UserRequest;
import com.alejo.rentadevehiculos.api.models.response.UserResponse;
import com.alejo.rentadevehiculos.domain.entities.UserEntity;
import com.alejo.rentadevehiculos.domain.repositories.PaymentMethodRepository;
import com.alejo.rentadevehiculos.domain.repositories.UserRepository;
import com.alejo.rentadevehiculos.infrastructure.abstractServices.IUserService;
import com.alejo.rentadevehiculos.util.exceptions.UserNotFoundExeption;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Data
@AllArgsConstructor
@Builder
@Slf4j
public class UserService implements IUserService {

    private final PaymentMethodRepository paymentMethodRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;
    private final PaymentMethodService paymentMethodService; // Agrega esta línea

    @Override
    public void userCreate(UserRequest userRequest) {
        UserEntity user = new UserEntity();
        user.setUsername(userRequest.getUsername());
        user.setFirstName(userRequest.getFirstName());
        user.setIsActive(true);
        user.setLastName(userRequest.getLastName());
        user.setRegistrationDate(LocalDateTime.now());
        user.setPassword(encryptPassword(userRequest.getPassword()));
        user.setPaymentMethods(new HashSet<>());
        log.info(String.valueOf(user));

        userRepository.save(user);

        log.info(String.valueOf(user));
    }

    @Override
    public ResponseEntity<?> updatePasswordUser(UpdatePasswordRequest updatePasswordRequest) {
        return null;
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
        UserResponse userResponse = new UserResponse();
        Optional<UserEntity> user = userRepository.findUserById(id);
        return user.map(this::toUserResponse).orElseThrow(()-> new UserNotFoundExeption("Error usuario no existe"));
    }

    @Override
    public Map<String, String> deleteUserBy(Long id) {
        UserEntity userEntity = userRepository.findUserById(id).orElseThrow(()->
                new UserNotFoundExeption("Error el usuario no existe"));
        userEntity.setIsActive(false);
        userRepository.save(userEntity);
        return Collections.singletonMap("msg","Usuario elimidado de manera correcta");
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
