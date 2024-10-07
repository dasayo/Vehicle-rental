package com.alejo.rentadevehiculos.domain.entities;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;
import java.util.Set;

@Entity(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private LocalDateTime registrationDate;
    private Boolean isActive;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
            mappedBy = "user",  // Cambiar de users a user
            fetch = FetchType.LAZY,  // Cambiar a LAZY
            cascade = CascadeType.ALL
    )
    private Set<PaymentMethodEntity> paymentMethods;
}
