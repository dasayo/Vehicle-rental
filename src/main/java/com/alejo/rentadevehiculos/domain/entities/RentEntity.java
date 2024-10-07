package com.alejo.rentadevehiculos.domain.entities;

import com.alejo.rentadevehiculos.util.Status;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "rent")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private BigDecimal value;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn( name = "id_payment_method")
    @JsonIgnore
    private PaymentMethodEntity paymentMethod;



    @ManyToOne
    @JoinColumn( name = "license_plate")
    @JsonBackReference
    private VehicleEntity vehicle;
}
