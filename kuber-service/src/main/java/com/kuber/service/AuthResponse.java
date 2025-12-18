package com.kuber.service;

import java.time.Instant;

public record AuthResponse(
    String userId,
    String email,
    String accessToken,
    String refreshToken,
    Instant expiresAt,
    boolean emailVerified,
    boolean phoneVerified
) {}