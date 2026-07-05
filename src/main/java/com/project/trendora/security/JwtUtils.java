package com.project.trendora.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtils {

    private static final String jwtSecret="VGhpc0lzTXlTdXBlclNlY3JldEtleUZvckp3dEF1dGhlbnRpY2F0aW9uMTIzNDU2Nzg5";
    private static final long jwtExpiration=86400000;

    //Extract username (subject) from JWT payload
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }
    //  Generic method to extract any claim from JWT
    public <T> T extractClaim(String token, Function<Claims,T> resolver){
        // Decode token and get all claims
        Claims claims=extractAllClaims(token);
        // Apply the required function (subject, expiration, etc.)
        return resolver.apply(claims);
    }

    // Generate JWT token
    public String generateToken(Map<String,Object> extraClaims, UserDetails userDetails){
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                // Sign token using secret key and HS256 algorithm
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                // Convert builder into JWT string
                .compact();
    }

    public boolean isTokenValid(String token,UserDetails userDetails){
        final String username=extractUsername(token);
        // Token is valid only if
        // 1. Username matches
        // 2. Token has not expired
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }
    // Decode JWT and return all claims
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Convert Base64 secret into Key object
    private Key getSigningKey(){
        // Decode Base64 string
        byte[] keyBytes=Decoders.BASE64.decode(jwtSecret);
        // Create signing key
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
