package com.uwoobeat.oidc.domain.member;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  private MemberRole role;

  private String oauthId;

  private String email;

  private String profileImageUrl;

  private LocalDateTime createdAt;

  @Builder(access = AccessLevel.PRIVATE)
  private Member(MemberRole role, String oauthId, String email, String profileImageUrl, LocalDateTime createdAt) {
    this.role = role;
    this.oauthId = oauthId;
    this.email = email;
    this.profileImageUrl = profileImageUrl;
  }

  public static Member createGuest(String oauthId, String email, String profileImageUrl) {
    return Member.builder()
        .role(MemberRole.GUEST)
        .oauthId(oauthId)
        .email(email)
        .profileImageUrl(profileImageUrl)
        .createdAt(LocalDateTime.now())
        .build();
  }

  public boolean isRecentlyJoined() {
    // 가입일로부터 7일 이내이면 최근 가입한 회원으로 판단
    return LocalDateTime.now().minusDays(7).isBefore(createdAt);
  }
}
