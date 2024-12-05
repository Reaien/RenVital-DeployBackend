package com.example.renvitalbackend.Repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.renvitalbackend.Entities.Respuesta;


public interface RespuestaRepository extends JpaRepository<Respuesta, Long>{
    List<Respuesta> findByPostId(Long postId);
}
