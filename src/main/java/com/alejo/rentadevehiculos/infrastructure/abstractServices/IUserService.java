package com.alejo.rentadevehiculos.infrastructure.abstractServices;

import com.alejo.rentadevehiculos.api.models.request.UpdatePasswordRequest;
import com.alejo.rentadevehiculos.api.models.request.UserRequest;
import com.alejo.rentadevehiculos.api.models.response.UserResponse;

import java.util.List;

public interface IUserService {

    void userCreate(UserRequest userRequest);
    void updatePasswordUser(UpdatePasswordRequest updatePasswordRequest);
    List<UserResponse> listUsers();
    UserResponse findUserByid(Long id);
    void deleteUserBy(Long id);

}
