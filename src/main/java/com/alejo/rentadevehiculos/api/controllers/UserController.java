package com.alejo.rentadevehiculos.api.controllers;

import com.alejo.rentadevehiculos.api.models.request.UpdatePasswordRequest;
import com.alejo.rentadevehiculos.api.models.request.UserRequest;
import com.alejo.rentadevehiculos.api.models.response.SuccesResponse;
import com.alejo.rentadevehiculos.api.models.response.UserResponse;
import com.alejo.rentadevehiculos.infrastructure.abstractServices.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicle-rental/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody UserRequest userRequest){
        userService.userCreate(userRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/list")
    public List<UserResponse> list(){
        return userService.listUsers();
    }

    @GetMapping("")
    public UserResponse getUser(@RequestParam Long id){
        return userService.findUserByid(id);
    }

    @DeleteMapping("")
    public ResponseEntity<SuccesResponse> deleteUser(@RequestParam Long id){
        userService.deleteUserBy(id);
        return ResponseEntity.ok(new SuccesResponse("Usuario eliminado de manera correcta"));
    }

    @PutMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordRequest updatePasswordRequest){
        userService.updatePasswordUser(updatePasswordRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }


}
