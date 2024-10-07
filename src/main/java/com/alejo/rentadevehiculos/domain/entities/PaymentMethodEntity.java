package com.alejo.rentadevehiculos.domain.entities;

import com.alejo.rentadevehiculos.util.Method;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity(name = "payment_method")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PaymentMethodEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Method method;

    private String cardNumber;

    private String lastDigits;

    private Boolean isActive;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_user", nullable = false)
    private UserEntity user;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "paymentMethod"
    )
    private  Set<RentEntity> rents;

}
