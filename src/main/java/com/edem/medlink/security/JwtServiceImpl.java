package com.edem.medlink.security;

import com.edem.medlink.exception.NotAuthenticatedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtServiceImpl implements JwtService{
    @Value("${secret-key}")
    private String secret;

    @Value("${jwt-token-expiration}")
    private int tokenExpirationTime;
    private static final String JWT_EXC_MSG = "Could not authenticate you, try again";
    @Override
    public String extractEmail(String jwtToken) {
        return extractSingleClaim(jwtToken, Claims::getSubject);
    }

    public <T> T extractSingleClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        try {
            final Claims claims = extractAllClaims(jwtToken);
            return claimsResolver.apply(claims);
        } catch (Exception e) {
            throw new NotAuthenticatedException(JWT_EXC_MSG);
        }
    }
    private Claims extractAllClaims(String jwtToken){
        try{
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(jwtToken)
                    .getBody();
        } catch (Exception e ){
            throw new NotAuthenticatedException(JWT_EXC_MSG);
        }
    }

    private Key getSignInKey(){
        byte[] byteKey = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(byteKey);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        Set<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        extraClaims.put("role", String.join(",", roles));

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (tokenExpirationTime * 1000L))) // Convert seconds to milliseconds
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    @Override
    public String extractSubject(String jwtToken) {
        return extractSingleClaim(jwtToken, Claims::getSubject);
    }

    public boolean isTokenValid(String jwtToken, UserDetails userDetails) {
        final String userEmail = extractEmail(jwtToken);
        return userEmail.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken);
    }
    private boolean isTokenExpired(String jwtToken) {
        return extractExpiration(jwtToken).before(new Date());
    }
    public Date extractExpiration(String jwtToken) {
        return extractSingleClaim(jwtToken, Claims::getExpiration);
    }
}
