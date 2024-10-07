package com.alejo.rentadevehiculos.infrastructure.abstractServices;

import com.alejo.rentadevehiculos.api.models.request.UpdatePasswordRequest;
import com.alejo.rentadevehiculos.api.models.request.UserRequest;
import com.alejo.rentadevehiculos.api.models.response.UserResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface IUserService {

    void userCreate(UserRequest userRequest);
//    public ResponseEntity userLogin(UserLoginRequest userLoginRequest);
    ResponseEntity<?> updatePasswordUser(UpdatePasswordRequest updatePasswordRequest);
    List<UserResponse> listUsers();
    UserResponse findUserByid(Long id);
    Map<String, String> deleteUserBy(Long id);

}
