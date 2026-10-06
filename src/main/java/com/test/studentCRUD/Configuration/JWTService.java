package com.test.studentCRUD.Configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JWTService {

    private final String secretKey = "infiuwefubufq9ubf9h340f3ifnionf2i3fi";
    //now as the secret key doen done after this we have to create the key
    private final SecretKey  key =  Keys.hmacShaKeyFor(secretKey.getBytes());


    public String generateToken (String email,String name){
        return Jwts.builder()
                .setSubject(email)
                .claim("name",name)
                .claim("email",email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(key)
                .compact();
    }

    public Claims getClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getEmailFromToken(String token){
        String RemoveBearerPrefix = token.replace("Bearer ", "");
        //RemoveBearerPrefix = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyaXRpazExQGdtYWlsLmNvbSIsIm5hbWUiOiJSaXRpayBTb25pIiwiZW1haWwiOiJyaXRpazExQGdtYWlsLmNvbSIsImlhdCI6MTc5MTI2OTQ1MSwiZXhwIjoxNzkxMzA1NDUxfQ.2A5QtjB1euPhKRfjLXSlYNMOGV__SdwANJ0ugI8qIqM"
        return getClaims(RemoveBearerPrefix).get("email",String.class);

    }

    public String getNameFromToken(String token){
        String RemoveBearerPrefix = token.replace("Bearer ", "");

        return getClaims(RemoveBearerPrefix).get("name",String.class);
    }
    public boolean validateToken(String token){
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;

        }
        catch (JwtException | IllegalArgumentException e ){
            return false;
        }

    }
    public boolean validateAuthorizationHeader(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return false;
        }
        String token = authorizationHeader.substring(7);
        return validateToken(token);
    }
}
