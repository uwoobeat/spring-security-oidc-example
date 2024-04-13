package com.uwoobeat.oidc.global.security;

import com.uwoobeat.oidc.domain.member.Member;
import com.uwoobeat.oidc.domain.member.MemberRole;
import lombok.Getter;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

@Getter
public class CustomOidcUser extends DefaultOidcUser {

  private final Long memberId;
  private final MemberRole memberRole;

  private CustomOidcUser(OidcUser oidcUser, Member member) {
    super(oidcUser.getAuthorities(), oidcUser.getIdToken(), oidcUser.getUserInfo());
    this.memberId = member.getId();
    this.memberRole = member.getRole();
  }

  public static CustomOidcUser of(OidcUser oidcUser, Member member) {
    return new CustomOidcUser(oidcUser, member);
  }
}
