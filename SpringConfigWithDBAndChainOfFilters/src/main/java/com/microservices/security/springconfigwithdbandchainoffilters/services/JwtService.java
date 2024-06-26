package com.microservices.security.springconfigwithdbandchainoffilters.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Service
public class JwtService {

    private static final Logger log = LogManager.getLogger(JwtService.class);

    @Value("${security.jwt.secret-key}")
    private String secretKey;
    @Value("${security.jwt.expiration-time}")
    private Long jwtExpirationTime;

    public String extractUserNameFromToken(String token){
        return extractClaim(token, Claims::getSubject);
    }
    public boolean isTokenValid(UserDetails userDetails , String token){
        final String userName = extractUserNameFromToken(token);
        return ((userName.equals(userDetails.getUsername())) && !isTokenExpired(token));

    }
    public String generateToken(UserDetails useDetails){
        return buildToken(useDetails);
    }

    private String buildToken (UserDetails userDetails) {

        String jwtToken = Jwts.builder()
                .setHeader(setHeader(new HashMap<String,Object>()))
                .setClaims(setClaims(new HashMap<String ,Object>(),userDetails,jwtExpirationTime)) //Payload
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
        return jwtToken;
    }

    private Map<String, Object> setHeader (HashMap<String, Object> headers) {
        headers.putIfAbsent("type","JWT");
        headers.putIfAbsent("alg","HS256");
        return headers;
    }

    private HashMap<String, Object> setClaims (HashMap<String, Object> claims, UserDetails userDetails,Long jwtExpirationTime) {
        claims.put("sub", userDetails.getUsername());
        claims.put("iss", UUID.randomUUID().toString());
        claims.put("aud", "inbound-service");
        claims.put("iat", new Date(System.currentTimeMillis()));
        claims.put("jti", UUID.randomUUID().toString());
        claims.put("exp",new Date(System.currentTimeMillis() +jwtExpirationTime));
        return claims;
    }


    private boolean isTokenExpired (String token) {
        return extractExpirationTimeFromToken(token).before(new Date());
    }

    public Date extractExpirationTimeFromToken (String token) {
        return extractClaim(token ,Claims::getExpiration);
    }

    private <T> T extractClaim (String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims (String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignKey () {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
