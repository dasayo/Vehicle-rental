package com.alejo.rentadevehiculos.domain.entities;

import com.alejo.rentadevehiculos.util.Method;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity(name = "payment_method")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PaymentMethodEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Setter(AccessLevel.NONE)
    private Method method;

    @Setter(AccessLevel.NONE)
    private String cardNumber;

    private String lastDigits;

    private Boolean isActive;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_user", nullable = false)
    @Setter(AccessLevel.NONE)
    private UserEntity user;

    @Setter(AccessLevel.NONE)
    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "paymentMethod"
    )
    private Set<RentEntity> rents;

    public void assignEncryptedCardNumber(String encryptedCardNumber) {
        this.cardNumber = encryptedCardNumber;
    }
}
