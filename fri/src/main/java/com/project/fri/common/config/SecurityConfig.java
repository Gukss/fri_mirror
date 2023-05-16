package com.project.fri.common.config;

import com.project.fri.common.config.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * packageName    : com.project.fri.common.config fileName       : SecurityConfig author         :
 * SSAFY date           : 2023-05-15 description    : ===========================================================
 * DATE              AUTHOR             NOTE -----------------------------------------------------------
 * 2023-05-15        SSAFY       최초 생성
 */

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터(SecurityConfig)가 스프링 기본 필터 체인에 등록
@RequiredArgsConstructor
public class SecurityConfig {

  private final CorsConfig corsConfig;

  /*
  기존: WebSecurityConfigurerAdapter를 상속하고 configure매소드를 오버라이딩하여 설정하는 방법
  => 현재: SecurityFilterChain을 리턴하는 메소드를 빈에 등록하는 방식(컴포넌트 방식으로 컨테이너가 관리)
 */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
        .csrf().disable() // 쿠키 기반 인증방식일 때 사용 -> JWT를 활용하여 진행 -> csrf 토큰 검사를 비활성화
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 사용 X
        .and()
        .formLogin().disable()
        .httpBasic().disable()
        .apply(new MyCustomDsl())
        .and()
        .authorizeHttpRequests(authorize -> authorize                              //  인증절차에 대한 설정을 진행
            .antMatchers("/user/sign-in", "/error/**").permitAll()  // todo:인증없이 접근 가능
            .anyRequest().authenticated()                                          // 그 외의 모든 요청은 인증되어야 접근 가능
        )
        .build();
  }

  // custom filter 추가
  private class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {

    @Override
    public void configure(HttpSecurity http) throws Exception {
      AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
      http
          .addFilter(corsConfig.corsFilter())  // 인증 O -> 시큐리티 필터에 등록
          .addFilter(new JwtAuthenticationFilter(authenticationManager));
    }
  }
}
