package com.example.renvitalbackend.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserInfoDTO {
    private String name;
    private String email;
    private String picture;
    private String role;
  
}