package com.project.fri.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignInErrorResponse {
    private String errorMessage;
}
