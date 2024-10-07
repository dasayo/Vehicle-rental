package com.alejo.rentadevehiculos.domain.repositories;

import com.alejo.rentadevehiculos.domain.entities.RentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.Set;

public interface RentRepository extends JpaRepository<RentEntity,Long> {

    @Query("SELECT r FROM rent r " +
            "JOIN r.paymentMethod pm " +
            "JOIN pm.user u " +
            "WHERE u.id = :userId")
    Set<RentEntity> findRentsByUserId(Long userId);
}
