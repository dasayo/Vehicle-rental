package com.alejo.rentadevehiculos.api.controllers;

import com.alejo.rentadevehiculos.api.models.request.PaymentMethodRequest;
import com.alejo.rentadevehiculos.api.models.request.PaymentUpdateRequest;
import com.alejo.rentadevehiculos.api.models.response.SuccesResponse;
import com.alejo.rentadevehiculos.infrastructure.abstractServices.IPaymentMethodService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicle-rental/payment")
@Data
@Builder
@AllArgsConstructor
public class PaymentMethodController {
    private IPaymentMethodService paymentMethodService;

    @PostMapping("/add")
    public ResponseEntity<SuccesResponse> addPaymentMethod(@Valid @RequestBody PaymentMethodRequest paymentMethodRequest){
        return paymentMethodService.addPaymetMethod(paymentMethodRequest);
    }

    @PutMapping("/update")
    public ResponseEntity<SuccesResponse> updatePaymetMethod(@Valid @RequestBody PaymentUpdateRequest request) {
        return paymentMethodService.editPaymentMethod(request);
    }

    @DeleteMapping("/")
    protected ResponseEntity<SuccesResponse> deletePaymentMethod(@RequestParam Long id){
        return paymentMethodService.deletePaymentMethod(id);
    }
}
