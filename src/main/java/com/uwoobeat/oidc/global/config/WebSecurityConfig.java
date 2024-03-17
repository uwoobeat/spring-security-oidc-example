package com.uwoobeat.oidc.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    // 디폴트 세팅
    http.formLogin(AbstractHttpConfigurer::disable) // 폼 로그인 비활성화
        .csrf(AbstractHttpConfigurer::disable) // csrf 비활성화
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 비활성화
        .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll()); // 모든 요청 허용

    // OIDC 설정
    http.oauth2Login(Customizer.withDefaults()); // OAuth2 로그인 활성화

    return http.build();
  }
}
