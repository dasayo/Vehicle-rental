package com.alejo.rentadevehiculos.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Entity(name = "vehicle")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class VehicleEntity {

    @Id
    @Column(length = 50)
    private String licensePlate;

    @Column(length = 50)
    private String brand;

    private BigDecimal rate;

    @Column(length = 50)
    private String model;

    private Boolean isAvailable;

    private Boolean isActive;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "vehicle"
    )
    private Set<RentEntity> rents;


}
