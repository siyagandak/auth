package com.khoding.auth.security;

import com.khoding.auth.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);
    @Value("${app.jwtSecret}")
    private String jwtSecret;
    @Value("${app.jwtExpirationMs}")
    private Integer jwtExpiryTimeMs;

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpiryTimeMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public Boolean validateJwtToken(String token) {
        LOGGER.info("JWS secret {}", jwtSecret);
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
            return Boolean.TRUE;
        } catch (SignatureException sigException) {
            LOGGER.error("Invalid Jwt Signature: {}", sigException.getMessage());
        } catch (MalformedJwtException malformedJwtException) {
            LOGGER.error("Invalid JWT Token: {}", malformedJwtException.getMessage());
        } catch (ExpiredJwtException expiredJwtException) {
            LOGGER.error("JWT token is expired: {}", expiredJwtException.getMessage());
        } catch (UnsupportedJwtException unsupportedJwtException) {
            LOGGER.error("JWT token is unsupported: {}", unsupportedJwtException.getMessage());
        } catch (IllegalArgumentException illegalArgumentException) {
            LOGGER.error("Jwt claims is empty: {}", illegalArgumentException.getMessage());
        }
        return Boolean.FALSE;
    }

}
