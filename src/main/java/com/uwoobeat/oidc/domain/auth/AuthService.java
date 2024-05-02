package com.uwoobeat.oidc.domain.auth;

import com.uwoobeat.oidc.domain.member.Member;
import com.uwoobeat.oidc.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final IdTokenValidator idTokenValidator;
  private final JwtService jwtService;
  private final MemberRepository memberRepository;

  public SocialLoginResponse socialLoginMember(SocialLoginRequest request) {
    OidcUser oidcUser = idTokenValidator.getOidcUser(request.idToken());
    Member member = fetchOrCreate(oidcUser);
    AccessTokenDto accessToken = jwtService.createAccessToken(member.getId(), member.getRole());
    return SocialLoginResponse.of(member, accessToken);
  }

  private Member fetchOrCreate(OidcUser oidcUser) {
    return memberRepository
        .findByOauthId(oidcUser.getName())
        .orElseGet(() -> registerMember(oidcUser));
  }

  private Member registerMember(OidcUser oidcUser) {
    Member guest =
        Member.createGuest(
            oidcUser.getName(),
            oidcUser.getAttribute(StandardClaimNames.EMAIL),
            oidcUser.getAttribute(StandardClaimNames.PICTURE));
    return memberRepository.save(guest);
  }
}
