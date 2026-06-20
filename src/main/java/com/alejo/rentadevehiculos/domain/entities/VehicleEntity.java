package com.alejo.rentadevehiculos.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity(name = "vehicle")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class VehicleEntity {

    @Id
    @Column(length = 50)
    @Setter(AccessLevel.NONE)
    private String licensePlate;

    @Column(length = 50)
    @Setter(AccessLevel.NONE)
    private String brand;

    @Setter(AccessLevel.NONE)
    private BigDecimal rate;

    @Column(length = 50)
    @Setter(AccessLevel.NONE)
    private String model;

    private Boolean isAvailable;

    private Boolean isActive;

    @Setter(AccessLevel.NONE)
    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "vehicle"
    )
    private Set<RentEntity> rents;

}
