package com.alejo.rentadevehiculos.domain.entities;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;
import java.util.Set;

@Entity(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String firstName;
    private String lastName;

    @Setter(AccessLevel.NONE)
    private String password;

    private LocalDateTime registrationDate;
    private Boolean isActive;

    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private Set<PaymentMethodEntity> paymentMethods;

    public void assignEncryptedPassword(String encryptedPassword) {
        this.password = encryptedPassword;
    }
}
