package com.uwoobeat.oidc.domain.auth;

import com.uwoobeat.oidc.domain.member.MemberRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {

  private final JwtUtil jwtUtil;

  public AccessTokenDto createAccessToken(Long memberId, MemberRole memberRole) {
    return jwtUtil.generateAccessToken(memberId, memberRole);
  }
}
