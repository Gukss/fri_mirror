package com.project.fri.common.service;

import com.project.fri.common.config.jwt.JwtProvider;
import com.project.fri.common.dto.ResponseAuth;
import com.project.fri.exception.exceptino_message.NotFoundExceptionMessage;
import com.project.fri.user.dto.SignInUserRequest;
import com.project.fri.user.entity.User;
import com.project.fri.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * packageName    : com.project.fri.common.service fileName       : AuthServiceImpl author         :
 * SSAFY date           : 2023-05-16 description    : ===========================================================
 * DATE              AUTHOR             NOTE -----------------------------------------------------------
 * 2023-05-16        SSAFY       최초 생성
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final JwtProvider jwtProvider;
  private final UserRepository userRepository;

  @Override
  public ResponseAuth createToken(SignInUserRequest request) {

    // email, password 검증
    String email = request.getEmail();
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new NotFoundExceptionMessage(
            NotFoundExceptionMessage.NOT_FOUND_USER
        ));
//    user.getPassword().equals()

    // 유효한 값이면 토큰 발급


    return null;
  }

}
