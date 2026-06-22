package com.alejo.rentadevehiculos.infrastructure.services;

import com.alejo.rentadevehiculos.api.models.request.UpdatePasswordRequest;
import com.alejo.rentadevehiculos.api.models.request.UserRequest;
import com.alejo.rentadevehiculos.api.models.response.UserResponse;
import com.alejo.rentadevehiculos.domain.entities.UserEntity;
import com.alejo.rentadevehiculos.domain.repositories.UserRepository;
import com.alejo.rentadevehiculos.infrastructure.abstractServices.IUserService;
import com.alejo.rentadevehiculos.infrastructure.mappers.UserMapper;
import com.alejo.rentadevehiculos.util.exceptions.UserNotFoundExeption;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements IUserService {

    private final PasswordService passwordService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public void userCreate(UserRequest userRequest) {
        UserEntity user = UserEntity.builder()
                 .username(userRequest.getUsername())
                 .firstName(userRequest.getFirstName())
                 .lastName(userRequest.getLastName())
                 .isActive(true)
                 .registrationDate(LocalDateTime.now())
                 .password(passwordService.encode(userRequest.getPassword()))
                 .paymentMethods(new HashSet<>())
                 .build();

        userRepository.save(user);
    }

    @Override
    public void updatePasswordUser(UpdatePasswordRequest updatePasswordRequest) {
        UserEntity userEntity = userRepository.findUserById(updatePasswordRequest.getId())
                .orElseThrow(() -> new UserNotFoundExeption("Error el usuario no existe"));

        userEntity.assignEncryptedPassword(passwordService.encode(updatePasswordRequest.getNewPassword()));
        userRepository.save(userEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> listUsers() {
        return userRepository.listUser()
                .stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse findUserByid(Long id) {
        return userRepository.findUserById(id)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new UserNotFoundExeption("Error usuario no existe"));
    }

    @Override
    public void deleteUserBy(Long id) {
        UserEntity userEntity = userRepository.findUserById(id)
                .orElseThrow(() -> new UserNotFoundExeption("Error el usuario no existe"));
        userEntity.setIsActive(false);
        userRepository.save(userEntity);
    }

}
