package com.uwoobeat.oidc.global.security;

import com.uwoobeat.oidc.domain.member.Member;
import com.uwoobeat.oidc.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

@Slf4j
@RequiredArgsConstructor
public class CustomOidcUserService extends OidcUserService {

  private final MemberRepository memberRepository;

  @Override
  public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
    OidcUser oidcUser = super.loadUser(userRequest);

    Member member = fetchOrCreate(oidcUser);
    log.info("사용자 정보 조회: id={}, email={}", member.getOauthId(), member.getEmail());

    return CustomOidcUser.of(oidcUser, member);
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
    log.info("게스트 회원가입: id={}, email={}", guest.getOauthId(), guest.getEmail());
    return memberRepository.save(guest);
  }
}
