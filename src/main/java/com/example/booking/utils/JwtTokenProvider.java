package com.example.booking.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Date;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import com.example.booking.entity.RefreshToken;
import com.example.booking.entity.User;
import com.example.booking.exception.AuthException;
import org.springframework.security.core.userdetails.UserDetails;
import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

@Component
@AllArgsConstructor
public class JwtTokenProvider {

    private final UserDetailsService userDetailsService;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    @Value("${jwt.refreshExpiration}")
    private long refreshExpiration;

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }

    public String generateToken(Map<String, Object> extraClaims, String subject) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    public boolean isTokenValid(String token) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
            String subject = claims.getSubject();
            Date expiration = claims.getExpiration();
            Date issuedAt = claims.getIssuedAt();
            return Objects.nonNull(subject) && Objects.nonNull(expiration) && Objects.nonNull(issuedAt);
        } catch (SignatureException e) {
            throw AuthException.builder().message("Invalid JWT signature").build();
        } catch (MalformedJwtException e) {
            throw AuthException.builder().message("Invalid JWT token").build();
        } catch (UnsupportedJwtException e) {
            throw AuthException.builder().message("JWT token is unsupported").build();
        } catch (IllegalArgumentException e) {
            throw AuthException.builder().message("JWT claims string is empty").build();
        } catch (ExpiredJwtException e) {
            throw AuthException.builder().message("JWT token has expired").build();
        }
    }

    public SecretKey getSigningKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return authHeader.substring(7);
    }

    public RefreshToken createRefreshToken(String email){

        UserDetails attachedUser = userDetailsService.loadUserByUsername(email);
        RefreshToken refToken =   new RefreshToken();
        refToken.setUser((User) attachedUser);
        refToken.setEmail(email);
        refToken.setToken(UUID.randomUUID().toString());
        refToken.setExpirationDate(new Date(System.currentTimeMillis() + refreshExpiration));

        return refToken;
    }


}
