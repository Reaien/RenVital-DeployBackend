package com.example.renvitalbackend.Controllers;

import org.springframework.web.bind.annotation.RestController;
import com.example.renvitalbackend.DTO.UserInfoDTO;
import com.example.renvitalbackend.Entities.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.renvitalbackend.Service.UserService;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    
    @GetMapping("/user-info")
    public ResponseEntity<UserInfoDTO> getUserInfo(@AuthenticationPrincipal OAuth2User principal) {
        // Obtener el email del usuario autenticado desde el token de Google
        String email = principal.getAttribute("email");
        
        // Buscar el usuario en tu base de datos
        UserEntity userEntity = userService.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    
        // Crear DTO con información completa
        UserInfoDTO userInfoDTO = new UserInfoDTO(
            userEntity.getName(),
            userEntity.getEmail(),
            userEntity.getPicture(),
            userEntity.getRole().name()
        );

        return ResponseEntity.ok(userInfoDTO);
    
    }
}
