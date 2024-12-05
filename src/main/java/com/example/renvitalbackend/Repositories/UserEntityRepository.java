package com.example.renvitalbackend.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.renvitalbackend.Entities.UserEntity;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {


    Optional<UserEntity> findByEmail(String email);
}
