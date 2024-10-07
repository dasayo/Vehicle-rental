package com.alejo.rentadevehiculos.domain.repositories;

import com.alejo.rentadevehiculos.domain.entities.PaymentMethodEntity;
import com.alejo.rentadevehiculos.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethodEntity, Long> {

    @Query("select p from payment_method p where p.user = :user and p.isActive=true ")
    Set<PaymentMethodEntity> findAllByIdUser(UserEntity user);

    @Query("select p from payment_method p where p.id = :id and p.isActive=true ")
    Optional<PaymentMethodEntity> findPaymentByPaymentId(Long id);
}
