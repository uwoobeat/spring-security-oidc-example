package com.uwoobeat.oidc.domain.auth;

import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

@Component
public class IdTokenValidator {

  public OidcUser getOidcUser(String idToken) {
    return null;
  }
}
