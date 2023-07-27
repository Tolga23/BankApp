package com.thardal.bankapp.security.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenGenerator {
    @Value("${jwt.secret}")
    private String API_KEY;
    @Value("${jwt.expiration}")
    private Long EXPIRE_TIME;

    public String generateJwtToken(Authentication authentication) {
        JwtUserDetails jwtUserDetails = (JwtUserDetails) authentication.getPrincipal();

        Date expireDate = new Date((new Date()).getTime() + EXPIRE_TIME);

        String token = Jwts.builder()
                .setSubject(Long.toString(jwtUserDetails.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, API_KEY)
                .compact();

        return token;
    }

    public Long findUserIdByToken(String token) {
        String userIdStr = parseToken(token)
                .getBody()
                .getSubject();

        return Long.valueOf(userIdStr);
    }

    private Jws<Claims> parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(API_KEY)
                .parseClaimsJws(token);
    }

    public boolean validateToken(String authToken) {
        boolean isValid;

        try {
            Jws<Claims> claims = parseToken(authToken);

            isValid = !isTokenExpired(claims);
        } catch (Exception e) {
            isValid = false;
        }

        return isValid;
    }

    private boolean isTokenExpired(Jws<Claims> claims) {

        Date expirationDate = claims.getBody().getExpiration();

        return expirationDate.before(new Date());
    }
}
