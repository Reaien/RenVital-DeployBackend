package com.example.renvitalbackend.Service;

import java.util.Optional;

import com.example.renvitalbackend.Entities.UserEntity;

public interface UserService {
    
    Optional<UserEntity> findByEmail(String email);

    void save(UserEntity user);
}
