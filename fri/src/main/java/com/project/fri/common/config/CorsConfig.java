package com.project.fri.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * packageName    : com.project.fri.common.config fileName       : CorsConfig author         : SSAFY
 * date           : 2023-05-15 description    : ===========================================================
 * DATE              AUTHOR             NOTE -----------------------------------------------------------
 * 2023-05-15        SSAFY       최초 생성
 */

// Spring Security는 기본적으로 CORS 를 허용하지 않도록 설정되어있기 때문에 허용하고 싶다면 추가 설정을 해야 한다.
@Configuration
public class CorsConfig {

  @Bean // CorsFilter 빈을 생성
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); // UrlBasedCorsConfigurationSource 객체를 사용하여 URL 경로별로 CORS 구성을 등록
    CorsConfiguration config = new CorsConfiguration(); // CorsConfiguration 객체는 구체적인 CORS 구성을 정의하는 데 사용

    config.setAllowCredentials(true);  // 내 서버가 응답을 할 때, json을 자바스크립트에서 처리할 수 있게 할지를 설정
    config.addAllowedOrigin("*");      // 모든 ip에 응답을 허용
    config.addAllowedHeader("*");      // 모든 요청 헤더 허용
    config.addAllowedMethod("*");      // 모든 HTTP 메서드 허용 (POST, GET, PUT, DELETE, PATCH)

    source.registerCorsConfiguration("/api/**", config);  // /api/** 경로에 대해 정의한 CORS 구성을 등록
    return new CorsFilter(source);  // CorsFilter 빈은 Spring Security의 필터 체인에 추가
    // 서버는 클라이언트의 CORS 요청을 수락하고 적절한 응답 헤더를 반환하여 브라우저에서 요청을 처리한다
  }

}
