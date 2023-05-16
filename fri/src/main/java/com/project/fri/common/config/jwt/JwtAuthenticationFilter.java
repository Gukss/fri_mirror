package com.project.fri.common.config.jwt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * packageName    : com.project.fri.common.config.jwt fileName       : JwtAuthenticationFilter
 * author         : SSAFY date           : 2023-05-16 description    :
 * =========================================================== DATE              AUTHOR
 * NOTE ----------------------------------------------------------- 2023-05-16        SSAFY       최초
 * 생성
 */

// 스프링 시큐리티에서 UsernamePasswordAuthenticationFilter가 있음
// /login으로 요청해서 username, password 전송하면 (post)
// UsernamePasswordAuthenticationFilter가 동작함
// .formLogin().disable()로 해놔서 동작안한다.
// 해결방법 -> 이 필터를 다시 SecurityConfig에 등록해준다.
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {

    System.out.println("JwtAuthenticationFilter: 로그인 시도중");

    return super.attemptAuthentication(request, response);
  }
}
