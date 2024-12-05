package com.example.renvitalbackend.Service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.renvitalbackend.Entities.UserEntity;
import com.example.renvitalbackend.Repositories.UserEntityRepository;
import com.example.renvitalbackend.Service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    
    private final UserEntityRepository userEntityRepository;

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userEntityRepository.findByEmail(email);
    }

    @Override
    public void save(UserEntity user) {
        userEntityRepository.save(user);
    }

}
