package com.alejo.rentadevehiculos.domain.repositories;

import com.alejo.rentadevehiculos.api.models.response.UserResponse;
import com.alejo.rentadevehiculos.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity,Long> {

    @Query("select u.username, u.firstName, u.lastName, u.registrationDate from users u where u.isActive=true")
    List<Object[]> listUser();

    @Query("select u from users u where u.id = :id and u.isActive=true")
    Optional<UserEntity> findUserById(Long id);

}
