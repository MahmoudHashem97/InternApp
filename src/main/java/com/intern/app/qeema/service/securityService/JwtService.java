package com.intern.app.qeema.service.securityService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
//    59595AC5D543C5D47EA7FE8E5C5A9
    //private static final String SECRET_KEY ="/T4Z3q4ZPKJICrffdthdXN16kPSD6rbk+vQ0S7ojjt16fqa0dpcicSkCtYr2wZkPDApK4vxpKfGQWRhX0yZhHWRwe3MByUFxxATEAoqV9oSIkaLItPj66Lx7qo4uwSjewJ/ddbjumnjyNvYtNxTQgFo95n/nadgEw0gvVx++5dUhyBuscEfuIr09VVFovkHHdkWqxgt1ptl8hMlK2XoYMMZm2BYswqoDqxiPD2iaY9WHqdK0O6Iq3QvtV3VTI8ygKibDbIx305oIf55QdkU4batk8m0RnuQcyinWWQdcqTvOJYxwW4CFtXlh6YVA8YGJ8iBS3rGqwt4l3a6DVbbPIm8w/O6gAj4dy+LZFNOFWiY=";


    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    public String extractUsername(String token) {
        //the subject could be username or email //the subject of the token
        return extractClaims(token,Claims::getSubject) ;
    }

    public <T>T extractClaims(String token, Function<Claims,T>clamsResolver){
        final Claims claims =extractAllClaims(token);
        return clamsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }
    public String generateToken(
            Map<String,Object>extraClaims,
            UserDetails userDetails
    ){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();//compact is the method that generate a token
    }

    public boolean isTokenValid (String token ,UserDetails userDetails){
        final String username =extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token) ;
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
         return extractClaims(token,Claims::getExpiration);
    }

    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes) ;
    }
    private Claims extractAllClaims (String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
