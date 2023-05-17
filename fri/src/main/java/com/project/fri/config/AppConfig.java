package com.project.fri.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * packageName    : com.project.fri.config fileName       : AppConfig date           : 2023-04-18
 * description    :
 */
@Configuration
@RequiredArgsConstructor
public class AppConfig {

  private final EntityManager em;

  @Bean
  public JPAQueryFactory queryFactory() {
    return new JPAQueryFactory(em);
  }

}
