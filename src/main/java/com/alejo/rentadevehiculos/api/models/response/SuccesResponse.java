package com.alejo.rentadevehiculos.api.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SuccesResponse {
    private final String msg ="msg";
    private String valueMsg;
}
