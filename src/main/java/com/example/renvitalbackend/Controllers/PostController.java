package com.example.renvitalbackend.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.renvitalbackend.Entities.Post;
import com.example.renvitalbackend.Repositories.PostRepository;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {


    @Autowired
    private PostRepository postRepository;


    @GetMapping //get all
    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByFechaDescHoraDesc();
    }

    @GetMapping("/{id}") //get by id
    public Post getPostById(@PathVariable Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post no encontrado"));
    }

    @PostMapping //post post
    public Post createPost(@RequestBody Post post) {
        return postRepository.save(post);
    }

    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post no encontrado"));

        postRepository.delete(post);

        return "El post fue eliminado id de post: " + id;
    }
}
