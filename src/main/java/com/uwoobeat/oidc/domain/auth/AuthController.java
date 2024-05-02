package com.uwoobeat.oidc.domain.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/social-login")
  public ResponseEntity<SocialLoginResponse> memberSocialLogin(
      @RequestBody SocialLoginRequest request) {
    return ResponseEntity.ok(authService.socialLoginMember(request));
  }
}
