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


    public String generateToken (String email,String name, String role){
        return Jwts.builder()
                .setSubject(email)
                .claim("name",name)
                .claim("email",email)
                .claim("role",role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(key)
                .compact();
    }

    public Claims getClaims(String token){
        String cleanToken = token.startsWith("Bearer ") ? token.substring(7) : token;
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(cleanToken)
                .getBody();
    }

    public String getEmailFromToken(String token){
        return getClaims(token).get("email", String.class);
    }

    public String getRoleFromToken(String token){
        return getClaims(token).get("role", String.class);
    }

    public String getNameFromToken(String token){
        return getClaims(token).get("name", String.class);
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
