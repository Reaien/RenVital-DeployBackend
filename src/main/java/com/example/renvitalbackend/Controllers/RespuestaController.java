package com.example.renvitalbackend.Controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.renvitalbackend.Entities.Post;
import com.example.renvitalbackend.Entities.Respuesta;
import com.example.renvitalbackend.Repositories.PostRepository;
import com.example.renvitalbackend.Repositories.RespuestaRepository;

@RestController
@RequestMapping("/respuestas")
public class RespuestaController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private RespuestaRepository respuestaRepository;

    @GetMapping("/post/{postId}")
    public List<Respuesta> getRespuestasByPostId(@PathVariable Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("No se encontró el post con ID: " + postId));
        return post.getRespuestas();
    }


    
    @PostMapping("/post/{postId}")
    @PreAuthorize("hasRole('ROLE_MEDICO')")
    public Respuesta addRespuestaToPost(@PathVariable Long postId, @RequestBody Respuesta respuesta) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post no encontrado"));
        respuesta.setPost(post);
        return respuestaRepository.save(respuesta);
    }

}
