package com.project.fri.common.controller;

import com.project.fri.common.service.AuthService;
import com.project.fri.user.dto.SignInUserRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : com.project.fri.common.controller fileName       : authController author
 * : SSAFY date           : 2023-05-16 description    : ===========================================================
 * DATE              AUTHOR             NOTE -----------------------------------------------------------
 * 2023-05-16        SSAFY       최초 생성
 */
@RestController
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/user/sign-in")
  public String login(@Valid @RequestBody SignInUserRequest request) {

    authService.createToken(request);


    return null;
  }
}
