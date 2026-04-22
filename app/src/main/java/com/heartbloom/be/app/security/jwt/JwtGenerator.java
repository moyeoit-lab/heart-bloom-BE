package com.heartbloom.be.app.security.jwt;

import com.heartbloom.be.app.service.auth.dto.TokenResult;
import com.heartbloom.be.common.time.TimeProvider;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Slf4j
@Component
public class JwtGenerator {

    private final TimeProvider timeProvider;
    private final SecretKey key;
    private final long accessTtl;
    private final long refreshTtl;

    public JwtGenerator(TimeProvider timeProvider,
                        @Value("${jwt.secret}") String secret,
                        @Value("${jwt.access.expire-time}") long accessTtl,
                        @Value("${jwt.refresh.expire-time}")long refreshTtl) {
        this.timeProvider = timeProvider;
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.accessTtl = accessTtl;
        this.refreshTtl = refreshTtl;
    }

    public String generate(Long userId, Map<String, Object> claims, long ttl) {
        Instant now = timeProvider.nowInstant();
        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claims(claims)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(ttl)))
                .signWith(key)
                .compact();
    }

    public TokenResult generateAccess(Long userId, String email, boolean deleted) {
        String accessToken = generate(
                userId,
                Map.of("typ", "access", "email", email, "deleted", deleted),
                accessTtl
        );
        return new TokenResult(accessToken);
    }

}