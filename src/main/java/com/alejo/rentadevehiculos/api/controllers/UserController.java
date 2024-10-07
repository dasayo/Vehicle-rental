package com.alejo.rentadevehiculos.api.controllers;

import com.alejo.rentadevehiculos.api.models.request.UserRequest;
import com.alejo.rentadevehiculos.api.models.response.UserResponse;
import com.alejo.rentadevehiculos.infrastructure.abstractServices.IUserService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/vehicle-rental/user")
@Data
@Builder

@AllArgsConstructor
public class UserController {

    private IUserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody UserRequest userRequest){
        userService.userCreate(userRequest);

        return null;
    }

    @GetMapping("/list")
    public List<UserResponse> list(){
        return userService.listUsers();
    }

    @GetMapping("/")
    private UserResponse getUser(@RequestParam Long id){
        return userService.findUserByid(id);
    }

    @DeleteMapping("/")
    private Map<?,?> deleteUser(@RequestParam Long id){
        return userService.deleteUserBy(id);
    }



}
