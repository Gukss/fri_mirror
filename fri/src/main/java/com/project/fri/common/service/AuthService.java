package com.project.fri.common.service;

import com.project.fri.common.dto.ResponseAuth;
import com.project.fri.user.dto.SignInUserRequest;
import org.springframework.stereotype.Service;

/**
 * packageName    : com.project.fri.common.service fileName       : AuthService author         :
 * SSAFY date           : 2023-05-16 description    : ===========================================================
 * DATE              AUTHOR             NOTE -----------------------------------------------------------
 * 2023-05-16        SSAFY       최초 생성
 */
public interface AuthService {
  public ResponseAuth createToken(SignInUserRequest request);

}
