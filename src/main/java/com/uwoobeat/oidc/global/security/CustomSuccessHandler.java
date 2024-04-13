package com.uwoobeat.oidc.global.security;

import com.uwoobeat.oidc.domain.auth.AccessTokenDto;
import com.uwoobeat.oidc.domain.auth.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Slf4j
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private final JwtService jwtService;

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {

    CustomOidcUser oidcUser = (CustomOidcUser) authentication.getPrincipal();
    AccessTokenDto accessToken =
        jwtService.createAccessToken(oidcUser.getMemberId(), oidcUser.getMemberRole());

    response.addHeader("Authorization", "Bearer " + accessToken.tokenValue());

    log.info("엑세스 토큰 생성 완료: {}", accessToken.tokenValue());

    super.onAuthenticationSuccess(request, response, authentication);
  }
}
