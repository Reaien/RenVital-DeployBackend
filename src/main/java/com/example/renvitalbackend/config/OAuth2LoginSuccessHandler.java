package com.example.renvitalbackend.config;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.renvitalbackend.Entities.RegistrationSource;
import com.example.renvitalbackend.Entities.UserEntity;
import com.example.renvitalbackend.Entities.UserRole;
import com.example.renvitalbackend.Service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{


    private final UserService userService;


    @Value("${frontend.community.url}")
    private String frontendCommunityUrl;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        
        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        if ("google".equals(oAuth2AuthenticationToken.getAuthorizedClientRegistrationId())) {
            
            DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
            Map<String, Object> attributes =  principal.getAttributes();
            String email = attributes.getOrDefault("email", "").toString();
            String name = attributes.getOrDefault("name", "").toString();
            String picture = attributes.getOrDefault("picture", "").toString();

            userService.findByEmail(email).ifPresentOrElse(user -> {
                DefaultOAuth2User newUser = new DefaultOAuth2User(List.of(new SimpleGrantedAuthority(user.getRole().name())),
                attributes, "sub");
                Authentication securityAuth = new OAuth2AuthenticationToken(newUser, List.of(new SimpleGrantedAuthority(user.getRole().name())),
                oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());
                SecurityContextHolder.getContext().setAuthentication(securityAuth);
            }, () -> {
                UserEntity userEntity = new UserEntity();
                userEntity.setRole(UserRole.ROLE_USER);
                userEntity.setEmail(email);
                userEntity.setName(name);
                userEntity.setPicture(picture);
                userEntity.setSource(RegistrationSource.GOOGLE);
                userService.save(userEntity);

                DefaultOAuth2User newUser = new DefaultOAuth2User(List.of(new SimpleGrantedAuthority(userEntity.getRole().name())),
                attributes, "sub");
                Authentication securityAuth = new OAuth2AuthenticationToken(newUser, List.of(new SimpleGrantedAuthority(userEntity.getRole().name())),
                oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());
                SecurityContextHolder.getContext().setAuthentication(securityAuth);
            });

             // Agregar encabezado de la cookie
            String sessionId = request.getSession().getId();
            response.setHeader("Set-Cookie", "JSESSIONID=" + sessionId + "; SameSite=None; Secure; HttpOnly; Path=/");
        }
        this.setAlwaysUseDefaultTargetUrl(true);
        this.setDefaultTargetUrl(frontendCommunityUrl);
        super.onAuthenticationSuccess(request, response, authentication);

    }
    
}
