package com.project.fri.user.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SignInUserRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
