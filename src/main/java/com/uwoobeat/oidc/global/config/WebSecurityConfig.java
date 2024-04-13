package com.uwoobeat.oidc.global.config;

import com.uwoobeat.oidc.domain.auth.JwtService;
import com.uwoobeat.oidc.domain.member.MemberRepository;
import com.uwoobeat.oidc.global.security.CustomOidcUserService;
import com.uwoobeat.oidc.global.security.CustomSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class WebSecurityConfig {

  private final JwtService jwtService;
  private final MemberRepository memberRepository;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    // 디폴트 세팅
    http.formLogin(AbstractHttpConfigurer::disable) // 폼 로그인 비활성화
        .csrf(AbstractHttpConfigurer::disable) // csrf 비활성화
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 비활성화
        .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll()); // 모든 요청 허용

    // OIDC 설정
    http.oauth2Login(
        oauth2 ->
            oauth2
                .userInfoEndpoint(
                    userInfo -> userInfo.oidcUserService(customOidcUserService(memberRepository)))
                .successHandler(customSuccessHandler(jwtService))
                .failureHandler((request, response, exception) -> response.setStatus(401)));

    return http.build();
  }

  @Bean
  public CustomOidcUserService customOidcUserService(MemberRepository memberRepository) {
    return new CustomOidcUserService(memberRepository);
  }

  @Bean
  public CustomSuccessHandler customSuccessHandler(JwtService jwtService) {
    return new CustomSuccessHandler(jwtService);
  }
}
