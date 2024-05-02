package com.uwoobeat.oidc.domain.auth;

import com.uwoobeat.oidc.domain.member.Member;

public record SocialLoginResponse(Long memberId, String accessToken, boolean isRecentlyJoined) {
  public static SocialLoginResponse of(Member member, AccessTokenDto accessTokenDto) {
    return new SocialLoginResponse(
        member.getId(), accessTokenDto.tokenValue(), member.isRecentlyJoined());
  }
}
