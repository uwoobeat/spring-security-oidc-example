package com.uwoobeat.oidc.domain.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberRole {
  GUEST("ROLE_GUEST"),
  USER("ROLE_USER");

  private final String value;
}
