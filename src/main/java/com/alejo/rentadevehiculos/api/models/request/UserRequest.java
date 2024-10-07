package com.alejo.rentadevehiculos.api.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserRequest {

    private String username;
    private String firstName;
    private String lastName;
    private String password;

}
