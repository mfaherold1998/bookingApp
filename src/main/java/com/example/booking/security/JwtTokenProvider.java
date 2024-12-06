package com.example.booking.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app-jwt-expiration-milliseconds}")
    private long jwtExpirationDate;

    // generate JWT token
    public String generateToken(Authentication authentication){

        String username = authentication.getName();

        Date currentDate = new Date();

        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(key())
                .compact();

        return token;
    }

    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    // get username from JWT token
    public String getUsername(String token){

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key()) // Configurar clave de firma
                .build()
                .parseClaimsJws(token) // Analizar y validar el token JWT
                .getBody(); // Obtener claims del token

        // Retornar el sujeto (username)
        return claims.getSubject();
    }

    // validate JWT token
    public boolean validateToken(String token){
        try {
            // Usar parserBuilder para analizar y validar el token JWT
            Jwts.parserBuilder()
                    .setSigningKey(key()) // Configurar clave de firma
                    .build()
                    .parseClaimsJws(token); // Analizar y validar el token firmado

            return true; // El token es válido
        } catch (Exception e) {
            // Manejo de excepciones, por ejemplo, firma inválida o token expirado
            System.err.println("Error al validar el token: " + e.getMessage());
            return false; // El token no es válido
        }
    }


}
