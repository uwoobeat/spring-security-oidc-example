package com.uwoobeat.oidc.domain.auth;

import com.uwoobeat.oidc.domain.member.MemberRole;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  public static final long HOUR_TO_MILLI = 3600000;
  public static final long ACCESS_TOKEN_EXPIRATION_MILLI_TIME = 2 * HOUR_TO_MILLI;
  public static final String ACCESS_TOKEN_SECRET = "QNeH8pugUCrolvlKI8EYMFQ8Dk9XXOwT";
  public static final String ISSUER_NAME = "uwoobeat";
  public static final String TOKEN_ROLE_NAME = "role";

  public AccessTokenDto generateAccessToken(Long memberId, MemberRole memberRole) {
    Date issuedAt = new Date();
    Date expiredAt = new Date(issuedAt.getTime() + ACCESS_TOKEN_EXPIRATION_MILLI_TIME);
    Key key = getKey(ACCESS_TOKEN_SECRET);

    String tokenValue = buildToken(memberId, memberRole, issuedAt, expiredAt, key);
    return new AccessTokenDto(memberId, memberRole, tokenValue);
  }

  private Key getKey(String secret) {
    return Keys.hmacShaKeyFor(secret.getBytes());
  }

  private String buildToken(
      Long memberId, MemberRole memberRole, Date issuedAt, Date expiredAt, Key key) {

    JwtBuilder jwtBuilder =
        Jwts.builder()
            .setIssuer(ISSUER_NAME)
            .setSubject(memberId.toString())
            .setIssuedAt(issuedAt)
            .claim(TOKEN_ROLE_NAME, memberRole.name())
            .setExpiration(expiredAt)
            .signWith(key);

    return jwtBuilder.compact();
  }
}
