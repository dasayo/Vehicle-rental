package com.alejo.rentadevehiculos.api.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UpdatePasswordRequest {
    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;

}
