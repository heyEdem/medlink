package com.edem.medlink.security;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

public interface JwtService {
    String extractEmail(String jwtToken);

    default <T> T extractSingleClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        return null;
    }

    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);

    String generateToken(UserDetails userDetails);

    String extractSubject(String jwtToken);

    boolean isTokenValid(String jtToken, UserDetails userDetails);

    Date extractExpiration(String jwtToken);
}
