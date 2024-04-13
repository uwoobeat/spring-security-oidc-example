package com.uwoobeat.oidc.domain.auth;

import com.uwoobeat.oidc.domain.member.MemberRole;

public record AccessTokenDto(Long memberId, MemberRole memberRole, String tokenValue) {}
