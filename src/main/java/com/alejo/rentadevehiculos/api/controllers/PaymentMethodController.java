package com.alejo.rentadevehiculos.api.controllers;

import com.alejo.rentadevehiculos.api.models.request.PaymentMethodRequest;
import com.alejo.rentadevehiculos.api.models.request.PaymentUpdateRequest;
import com.alejo.rentadevehiculos.api.models.response.SuccesResponse;
import com.alejo.rentadevehiculos.infrastructure.abstractServices.IPaymentMethodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicle-rental/payment")
@RequiredArgsConstructor
public class PaymentMethodController {
    private final IPaymentMethodService paymentMethodService;

    @PostMapping("/add")
    public ResponseEntity<SuccesResponse> addPaymentMethod(@Valid @RequestBody PaymentMethodRequest paymentMethodRequest){
        paymentMethodService.addPaymetMethod(paymentMethodRequest);
        return ResponseEntity.ok(new SuccesResponse("Payment method added successfully"));
    }

    @PutMapping("/update")
    public ResponseEntity<SuccesResponse> updatePaymetMethod(@Valid @RequestBody PaymentUpdateRequest request) {
        paymentMethodService.editPaymentMethod(request);
        return ResponseEntity.ok(new SuccesResponse("Payment method updated successfully"));
    }

    @DeleteMapping("/")
    public ResponseEntity<SuccesResponse> deletePaymentMethod(@RequestParam Long id){
        paymentMethodService.deletePaymentMethod(id);
        return ResponseEntity.ok(new SuccesResponse("Payment method deleted successfully"));
    }
}
